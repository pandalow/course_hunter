package com.hunt.service;

import com.hunt.entity.User;
import com.hunt.vo.UserVO;

import java.util.Optional;

public interface UserService {
    UserVO handleGoogleOAuth(String code) throws Exception;
}
