package com.hunt.controller;

import com.hunt.result.Result;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/google")
    public Result<UserVO> signIn(@RequestBody Map<String, String> payload) throws Exception {
        /**
         * @param payload 包含前端发送的 Google OAuth 认证码
         * @return 返回包含访问令牌的结果对象
         * @throws IOException 处理认证过程中可能出现的输入输出异常
         */

        String code = payload.get("credential");
        UserVO userVo = userService.handleGoogleOAuth(code);
        return Result.success(userVo);
    }

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(){

        /**
         *
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() ||
         authentication instanceof AnonymousAuthenticationToken){
            return Result.error("Not Authenticated");
        }

        String email = authentication.getName();
        System.out.println(email);
        UserVO userVo = userService.getUserByGoogleId(email);

        return Result.success(userVo);
    }
}
