package com.hunt.service.impl;

import com.hunt.constant.EmailServiceConstant;
import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.constant.JwtConstant;
import com.hunt.dao.UserDAO;
import com.hunt.dto.UserInfoDTO;
import com.hunt.dto.UserLoginDTO;
import com.hunt.entity.User;
import com.hunt.exception.DuplicationException;
import com.hunt.exception.NotFoundException;
import com.hunt.exception.UnauthorizedActionException;
import com.hunt.service.EmailService;
import com.hunt.service.UserService;
import com.hunt.utils.Base64Util;
import com.hunt.utils.JwtUtil;
import com.hunt.utils.RedisUtil;
import com.hunt.utils.UsernameGeneratorUtil;
import com.hunt.vo.UserInfoVO;
import com.hunt.vo.UserLoginVO;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private EmailService emailService;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    /**
     * Logs in a user.
     *
     * @param userLoginDTO the login data transfer object containing email and password.
     * @return UserLoginVO containing user login information and token.
     * @throws UnauthorizedActionException if credentials are incorrect.
     */
    @Override
    public UserLoginVO userLogin(UserLoginDTO userLoginDTO) {
        // check if user exists
        User user = userRetrieveByEmail(userLoginDTO.getEmail());

        // check if credentials are correct
        String plainPassword = userLoginDTO.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = encoder.matches(plainPassword, encodedPassword);
        if (!isMatched) {
            throw new UnauthorizedActionException(ExceptionMessageConstant.WRONG_CREDENTIALS);
        }

        // on successful login, generate token and save to redis
        String token = JwtTokenGenerator(user);
        RedisUtil.saveToRedisWithExpire(user.getId().toString(), token, JwtConstant.TOKEN_DURATION);

        // return user login info
        UserLoginVO userLoginVO = userToUserLoginVO(user, token);
        return userLoginVO;
    }

    /**
     * Registers a new user.
     *
     * @param userLoginDTO the login data transfer object containing email and password.
     * @throws DuplicationException if a user with the provided email already exists.
     */
    @Override
    @Transactional
    public void userRegister(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        // check if email is illegal
        if (!isValidEmail(email)) {
            throw new UnauthorizedActionException(ExceptionMessageConstant.ILLEGAL_EMAIL);
        }

        if (userDAO.existsByEmail(email)) {
            throw new DuplicationException(ExceptionMessageConstant.USER_ALREADY_EXIST);
        }

        // if not exist create user
        String encryptedPassword = encoder.encode(userLoginDTO.getPassword());// encrypt password
        User user = userLoginDTOToUser(userLoginDTO);
        user.setPassword(encryptedPassword);
        // save to database
        userDAO.save(user);

        // Send token by email
        sendVerifyEmail(user.getEmail());
    }


    /**
     * Verifies token.
     *
     * @param emailToken the token used for email verification.
     * @throws UnauthorizedActionException if the token is invalid or expired.
     */
    @Override
    public void userVerify(String emailToken) {
        long userId;
        String email;
        // Decode the encoded string
        try {
            Map<String, Object> claims = Base64Util.decodeMap(emailToken);
            userId = Long.parseLong((String) claims.get("userId"));
            email = (String) claims.get("email");
            Instant expiration = Instant.parse((String) claims.get("expirationTime"));

            // if token is not exist in redis or token is not equal to the token in redis, throw exception
            String token = RedisUtil.getFromRedis(email, String.class);
            if (token == null || !token.equals(emailToken)) {
                throw new UnauthorizedActionException(ExceptionMessageConstant.ILLEGAL_TOKEN);
            }

            // Verify token expiration, if expired, throw exception
            boolean isExpired = isExpired(expiration);
            if (isExpired) {
                throw new UnauthorizedActionException(ExceptionMessageConstant.ILLEGAL_TOKEN);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnauthorizedActionException(ExceptionMessageConstant.ILLEGAL_TOKEN);
        }

        // deal with the case when user already verified
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessageConstant.USER_NOT_FOUND));
        if (user.isVerified()) {
            throw new UnauthorizedActionException(ExceptionMessageConstant.ILLEGAL_TOKEN);
        }

        // Update the user as verified
        user.setVerified(true);
        userDAO.saveAndFlush(user);

        // delete token in redis after verification to prevent reuse
        RedisUtil.deleteFromRedis(email);
    }

    /**
     * Sends a verification email.
     *
     * @param emailAddress the email address to send the verification link to.
     */
    @Override
    public void sendVerifyEmail(String emailAddress) {
        // check if token exists in redis
        String token = RedisUtil.getFromRedis(emailAddress, String.class);
        if (token == null) {
            // if not exist, generate token and save to redis
            token = emailTokenGenerator(emailAddress);
            RedisUtil.saveToRedisWithExpire(emailAddress, token, EmailServiceConstant.VERIFICATION_TOKEN_DURATION);
        }
        String verificationLink = createVerificationLink(token);
        // write token to log
        log.info("User: {}; token: {}; link: {}", emailAddress, token, verificationLink);

        // parse and send email
        String emailContent = emailService.emailBuilder(EmailServiceConstant.VERIFICATION_TEMPLATE, verificationLink);
        emailService.send(emailAddress, emailContent, EmailServiceConstant.VERIFICATION_ADDRESS, EmailServiceConstant.VERIFICATION_SUBJECT);
    }

    /**
     * @param userLoginDTO the data transfer object containing email and password.
     */
    @Override
    public void changePassword(UserLoginDTO userLoginDTO) {
        User user = userRetrieveByEmail(userLoginDTO.getEmail());
        String encodedPassword = encoder.encode(userLoginDTO.getPassword());
        user.setPassword(encodedPassword);
        userDAO.saveAndFlush(user);
    }

    /**
     * @param userId user id
     * @return
     */
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessageConstant.USER_NOT_FOUND));
        return userToUserInfoVO(user);
    }

    /**
     * @param userId  user id
     * @param userInfoDTO  user information data transfer object.
     */
    @Override
    public void updateUserInfo(Long userId, UserInfoDTO userInfoDTO) {
        log.info("Update user info: {}", userInfoDTO);
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessageConstant.USER_NOT_FOUND));
        BeanUtils.copyProperties(userInfoDTO, user);
        try {
            userDAO.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicationException(ExceptionMessageConstant.USERNAME_ALREADY_EXIST);
        }
    }

    /**
     * Creates a verification link.
     *
     * @param token the token to be included in the link.
     * @return the verification link.
     */
    private String createVerificationLink(String token) {
        // String verificationLink = JwtConstant.DOMAIN_NAME + "user/verify/" + token;
        String verificationLink = "http://localhost:8080/user/verify/" + token;
        return verificationLink;
    }

    /**
     * Checks if a token is expired.
     *
     * @param expiration the expiration time of the token.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isExpired(Instant expiration) {
        // return true if the token is expired
        return expiration.isBefore(Instant.now());
    }


    /**
     * Retrieves user information by email.
     *
     * @param email the email address of the user.
     * @return UserInfoVO containing user information.
     */
    private User userRetrieveByEmail(String email) {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageConstant.USER_NOT_FOUND));
        return user;
    }

    /**
     * Generates a JWT token for API call authentication.
     *
     * @param user the user entity.
     * @return a JWT token.
     */
    private String JwtTokenGenerator(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.USER_INFO_ID, user.getId().toString());
        claims.put(JwtConstant.USER_INFO_EMAIL, user.getEmail());
        String token = JwtUtil.createJWT(claims);
        return token;
    }

    /**
     * Generates token for email verification.
     *
     * @param email user Email address.
     * @return an encoded email verification token.
     */
    private String emailTokenGenerator(String email) {
        User user = userRetrieveByEmail(email);
        Long userId = user.getId();

        Instant currentTime = Instant.now();
        Instant expirationTime = currentTime.plusSeconds(3 * 24 * 60 * 60); // Adding 3 days in seconds

        // Create a map with user ID and expiration time
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("userId", userId.toString());
        userData.put("expirationTime", expirationTime.toString());

        // Encode the map
        String token = Base64Util.encodeMap(userData);

        return token;
    }

    /**
     * Converts a UserLoginDTO to a User entity.
     *
     * @param userLoginDTO the data transfer object containing user login information.
     * @return User the user entity.
     */
    private User userLoginDTOToUser(UserLoginDTO userLoginDTO) {
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO, user);
        user.setVerified(false);
        user.setUsername(UsernameGeneratorUtil.generateUniqueUsername(userLoginDTO.getEmail()));
        return user;
    }

    /**
     * Converts a User entity to a UserInfoVO value object.
     *
     * @param user the user entity.
     * @return UserInfoVO the user information value object.
     */
    private UserInfoVO userToUserInfoVO(User user) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }


    /**
     * Converts a User entity to a UserLoginVO value object.
     *
     * @param user the user entity.
     * @return UserLoginVO the user login value object.
     */
    private UserLoginVO userToUserLoginVO(User user, String token) {
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);
        userLoginVO.setTokenSSO(token);
        return userLoginVO;
    }


    /**
     * check Email is valid or not
     *
     * @param email email address
     * @return true if email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        boolean isValid = true;
        try {
            new InternetAddress(email).validate();
        } catch (AddressException e) {
            isValid = false;
        }
        return isValid;
    }

}
