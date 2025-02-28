package com.hunt.controller;

import com.hunt.entity.User;
import com.hunt.exception.BaseException;
import com.hunt.result.Result;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null){
            throw new BaseException("User not found");
        }

        String googleId = oidcUser.getSubject();
        UserVO user = service.getUserByGoogleId(googleId);

        return Result.success(user);
    }
    @PostMapping("/logout")
    public Result logout() {
        return Result.success("Logout success");
    }

}
