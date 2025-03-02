package com.hunt.service;

import com.hunt.entity.User;
import com.hunt.vo.UserVO;

public interface UserService {

    void processOAuthPostLogin(String googleId, String name, String email, String avatarUrl);

    UserVO getUserByGoogleId(String googleId);
}
