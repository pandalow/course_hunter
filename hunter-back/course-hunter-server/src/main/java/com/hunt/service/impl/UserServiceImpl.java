package com.hunt.service.impl;

import com.hunt.dao.UserDAO;
import com.hunt.entity.User;
import com.hunt.service.UserService;
import com.hunt.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userRepository;

    private final StringRedisTemplate redisTemplate;
    private static final String TOKEN_PREFIX = "auth:token:";

    @Override
    public UserVO processOAuthPostLogin(String googleId, String name, String email, String avatarUrl) {

        Optional<User> userOptional = userRepository.findByGoogleId(googleId);

        if (userOptional.isEmpty()) {
            // 如果 Google 账户不存在，创建新用户
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setAvatar(avatarUrl);
            userRepository.save(newUser);

            return new UserVO(newUser.getId(), googleId, email, name, avatarUrl);
        } else {
            // 获取已有用户
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
                userRepository.save(existUser);
            }

            return new UserVO(existUser.getId(), existUser.getGoogleId(), existUser.getEmail(), existUser.getName(), existUser.getAvatar());
        }
    }

    @Override
    public UserVO getUserByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId)
                .map(user -> new UserVO(
                        user.getId(),
                        user.getGoogleId(),
                        user.getEmail(),
                        user.getName(),
                        user.getAvatar()
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public void saveAccessToken(String googleId, String accessToken) {
        redisTemplate.opsForValue().set(TOKEN_PREFIX + accessToken, googleId, 1, TimeUnit.DAYS); // 1 小时过期
    }

    @Override
    public Optional<String> getGoogleIdByAccessToken(String accessToken) {
        String googleId = redisTemplate.opsForValue().get(TOKEN_PREFIX + accessToken);
        return Optional.ofNullable(googleId);
    }
}
