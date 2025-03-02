package com.hunt.service.impl;

import com.hunt.dao.UserDAO;
import com.hunt.entity.User;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userRepository;

    @Override
    public void processOAuthPostLogin(String googleId, String name, String email, String avatarUrl) {
        Optional<User> userOptional = userRepository.findByGoogleId(googleId);

        if (userOptional.isEmpty()) {
            // 如果 Google 账户不存在，创建新用户
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setAvatar(avatarUrl);
            userRepository.save(newUser);
        } else {
            // 如果用户已存在，检查是否需要更新信息
            User existUser = userOptional.get();
            boolean isUpdated = false;

            if (!existUser.getName().equals(name)) {
                existUser.setName(name);
                isUpdated = true;
            }
            if (!existUser.getAvatar().equals(avatarUrl)) {
                existUser.setAvatar(avatarUrl);
                isUpdated = true;
            }

            if (isUpdated) {
                userRepository.save(existUser); // 仅在信息有变更时更新数据库
            }
        }
    }

    @Override
    public UserVO getUserByGoogleId(String googleId) {
        Optional<User> userOptional = userRepository.findByGoogleId(googleId);
        return userOptional.map(
                user -> new UserVO(
                        user.getId(),
                        user.getGoogleId(),
                        user.getEmail(),
                        user.getName(),
                        user.getAvatar()
                )).orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
