package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.UserDAO;
import com.hunt.entity.User;
import com.hunt.enumerate.Role;
import com.hunt.service.UserService;
import com.hunt.utils.JwtUtils;
import com.hunt.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userRepository;

    @Autowired
    private GoogleIdTokenVerifier verifier;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserVO handleGoogleOAuth(String code) throws Exception {
        /*
         * Achieve Google OAuth verified processing by credential(TokenId)
         * @args: credential
         * @return: UserVO(include JWT)
         */

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
        User user = userRepository.findByEmail(email).orElseGet(
                ()->{return userRepository.save(User.builder()
                            .email(email)
                            .name(name)
                            .googleId(userId)
                            .role(Role.User)
                            .avatar(pictureUrl)
                            .build()
                            );
                });
        //Create JWT tokens by jjwt lib
        String tokens = jwtUtils.generateToken(user);

        //  return UserVO + tokens
        return UserVO.builder()
                .token(tokens)
                .name(user.getName())
                .avatar(user.getAvatar())
                .build();
    }
}
