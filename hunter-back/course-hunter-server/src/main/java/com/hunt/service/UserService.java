package com.hunt.service;

import com.hunt.dto.UserInfoDTO;
import com.hunt.dto.UserLoginDTO;
import com.hunt.vo.UserInfoVO;
import com.hunt.vo.UserLoginVO;

public interface UserService {
    UserLoginVO userLogin(UserLoginDTO userLoginDTO);
    void userRegister(UserLoginDTO userLoginDTO);
    void userVerify(String token);
    void sendVerifyEmail(String emailAddress);
    void changePassword(UserLoginDTO userLoginDTO);

    UserInfoVO getUserInfo(Long userId);

    void updateUserInfo(Long userId, UserInfoDTO userInfoDTO);
}
