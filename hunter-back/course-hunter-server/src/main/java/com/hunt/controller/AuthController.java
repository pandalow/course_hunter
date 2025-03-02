package com.hunt.controller;

import com.alibaba.fastjson.JSONObject;
import com.hunt.result.Result;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private UserService userService;


    @RequestMapping("/me")
    public Object me(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 Bearer 前缀
            return userService.getGoogleIdByAccessToken(token)
                    .map(Result::success)
                    .orElse(Result.error("Invalid Token"));
        }
        return Result.error("Unauthorized");
    }

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();

        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    @RequestMapping("/callback/google")
    public void login(AuthCallback callback, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);

        if (!authResponse.ok()) {
            response.sendRedirect("http://localhost:5173?error=" + authResponse.getMsg()); // 重定向到前端首页并显示错误
            return;
        }

        AuthUser authUser = authResponse.getData();
        String googleId = authUser.getUuid();
        String username = authUser.getUsername();
        String email = authUser.getEmail();
        String avatar = authUser.getAvatar();
        String accessToken = authUser.getToken().getAccessToken();

        // 存储用户信息
        UserVO user = userService.processOAuthPostLogin(googleId, username, email, avatar);
        userService.saveAccessToken(googleId, accessToken);

        // **重定向到前端首页，附带 accessToken**
        response.sendRedirect("http://localhost:5173?accessToken=" + accessToken);
    }


    private AuthRequest getAuthRequest() {
        return new AuthGoogleRequest(AuthConfig.builder()
                .clientId("234209469090-365ddt4bofoscqjlr8ml8nv4e7f7dpc6.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri("http://localhost:9999/oauth/callback/google")
                .build());
    }
}
