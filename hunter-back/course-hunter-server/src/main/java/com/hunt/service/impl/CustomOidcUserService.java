package com.hunt.service.impl;

import com.hunt.exception.UnauthorizedActionException;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserService userService;  // 用户业务服务，用于访问数据库

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("✅ OAuth 认证成功，执行 loadUser() 方法");

        OidcUser oidcUser = super.loadUser(userRequest);

        // 提取 Google 用户的关键信息
        String googleId = oidcUser.getAttribute("sub");
        String name = oidcUser.getAttribute("name");
        String email = oidcUser.getAttribute("email");
        String avatar = oidcUser.getAttribute("picture");

        // **打印日志，确认是否进入 loadUser() 方法**
        System.out.println("✅ OAuth 登录成功，Google ID: " + googleId);
        System.out.println("✅ 用户信息: " + name + ", " + email + ", " + avatar);

        // **调用 userService 进行用户存储**
        userService.processOAuthPostLogin(googleId, name, email, avatar);

        return oidcUser;
    }
}
