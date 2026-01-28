package com.hunt.service;

import com.hunt.vo.UserVO;

public interface UserService {
    UserVO handleGoogleOAuth(String code) throws Exception;


    UserVO getUserByGoogleId(String googleId);
}
