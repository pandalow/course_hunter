package com.hunt.controller;

import com.hunt.result.Result;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Authenticate user via Google Oauth 2.0
     *
     * @param payload contains Oauth 2.0 verification code
     * @return userVO including app-tokens
     */
    @PostMapping("/login/google")
    public Result<UserVO> signIn(@RequestBody Map<String, String> payload) throws Exception {
        String code = payload.get("credential");
        UserVO userVo = userService.handleGoogleOAuth(code);
        return Result.success(userVo);
    }

    /**
     * Get authentication id (google ID) from SecurityContext
     * Helper function to optimizing automate login logic
     * @return UserVO of current authenticated user
     */
    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(){

        Authentication authentication = SecurityContextHolder
                                            .getContext()
                                            .getAuthentication();
        if(authentication == null 
            || !authentication.isAuthenticated() 
            || authentication instanceof AnonymousAuthenticationToken){
            return Result.error("Not Authenticated");
        }

        String googleId = authentication.getName();
        UserVO userVo = userService.getUserByGoogleId(googleId);

        return Result.success(userVo);
    }
}
