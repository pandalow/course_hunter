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
        // Verified credential code;
        GoogleIdToken idToken = verifier.verify(code); // Already instanced by a BEAN in GoogleAuthConfig
        if(idToken == null){
            throw new RuntimeException(ExceptionMessageConstant.WRONG_CREDENTIALS);
        }

        // 2. 自动注册/登录（核心业务逻辑）：
        //      查库：拿着 Token 里的 Email 去数据库里搜。
        //      注册：如果搜不到，就直接用 Google 提供的信息（姓名、头像、Email）新建一个 User 记录.
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
