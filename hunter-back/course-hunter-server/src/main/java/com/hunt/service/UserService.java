package com.hunt.service;

import com.hunt.entity.User;
import com.hunt.vo.UserVO;

import java.util.Optional;

public interface UserService {

    UserVO processOAuthPostLogin(String googleId, String name, String email, String avatarUrl);

    UserVO getUserByGoogleId(String googleId);
    void saveAccessToken(String googleId, String accessToken);
    Optional<String> getGoogleIdByAccessToken(String accessToken);
}
