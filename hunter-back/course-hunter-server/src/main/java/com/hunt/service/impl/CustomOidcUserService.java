package com.hunt.service.impl;

import com.hunt.exception.UnauthorizedActionException;
import com.hunt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserService userService;  // 用户业务服务，用于访问数据库

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            // 先使用默认实现获取 OIDC 用户信息
            OidcUser oidcUser = super.loadUser(userRequest);

            // 提取 Google 用户的关键信息
            String googleId = oidcUser.getAttribute("sub");       // Google唯一ID
            String name = oidcUser.getAttribute("name");          // 用户姓名
            String email = oidcUser.getAttribute("email");        // 邮箱
            String avatar = oidcUser.getAttribute("picture");     // 头像URL

            // 调用用户服务，检查是否已存在此用户，如不存在则创建新用户
            userService.processOAuthPostLogin(googleId, name, email, avatar);

            // 返回 oidcUser 以让 Spring Security 完成认证流程
            return oidcUser;
        } catch(Exception e) {
            throw new UnauthorizedActionException("Cannot process OAuth2 login: " + e.getMessage());
        }
    }
}
