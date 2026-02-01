package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.UserDAO;
import com.hunt.entity.User;
import com.hunt.enumerate.Role;
import com.hunt.service.UserService;
import com.hunt.utils.JwtUtils;
import com.hunt.vo.UserVO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.transaction.annotation.Transactional;


/**
 * User Service Implementation
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userRepository;
    private final GoogleIdTokenVerifier verifier;
    private final JwtUtils jwtUtils;

    /**
     * Achieve Google OAuth verified processing by credential(TokenId)
     * @param code token creds
     * @return UserVO(include JWT)
     */
    @Override
    @Transactional
    public UserVO handleGoogleOAuth(String code) throws Exception {
        // 1. Verified credential code;
        GoogleIdToken idToken = verifier.verify(code); // Already instanced by a BEAN in GoogleAuthConfig
        if(idToken == null){
            throw new RuntimeException(ExceptionMessageConstant.WRONG_CREDENTIALS);
        }


        // 2. Automatic Register/Login (Core Business Logic):
        //      Find User by Google ID in Token
        //      If found, Login;
        //      If not found, Register a new User;
        
        Payload payload = idToken.getPayload();

        String userId = payload.getSubject();

        // Get profile information from payload
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        // Get User or Create User
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setGoogleId(userId);
            newUser.setRole(Role.User);
            newUser.setAvatar(pictureUrl);
            return userRepository.save(newUser);
        });

        //Create JWT tokens by jjwt lib
        String tokens = jwtUtils.generateToken(user);

        //  return UserVO + tokens
        return UserVO.builder()
                .id(user.getId())
                .token(tokens)
                .name(user.getName())
                .mail(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

    /**
     * Get UserVO by googleId
     *
     * @param googleId Google ID from OAuth
     * @return UserVO
     */
    @Override
    public UserVO getUserByGoogleId(String googleId) {
        System.out.println(googleId);
        User user = userRepository.findByGoogleId(googleId).orElseThrow(
                ()-> new RuntimeException("User Not Found"));

        return UserVO.builder()
                .id(user.getId())
                .name(user.getName())
                .mail(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }
}
