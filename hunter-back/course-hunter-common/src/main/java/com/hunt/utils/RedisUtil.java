package com.hunt.utils;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@NoArgsConstructor
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
    }

    /**
     * @param key      key
     * @param value    value
     * @param duration expiration time
     */
    public static <T> void saveToRedisWithExpire(String key, T value, long duration) {
        redisUtil.redisTemplate.opsForValue().set(key, value, duration, TimeUnit.MILLISECONDS);
    }


    /**
     * @param key   key
     * @param value value
     */
    public static <T> void saveToRedisWithoutExpire(String key, T value) {
        redisUtil.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key key
     * @return value
     */
    public static <T> T getFromRedis(String key, Class<T> clazz) {
        return clazz.cast(redisUtil.redisTemplate.opsForValue().get(key));
    }

    /**
     * @param key key
     */
    public static void deleteFromRedis(String key) {
        redisUtil.redisTemplate.delete(key);
    }

    /**
     * @param key key
     * @return true if key exists
     */
    public static boolean existsInRedis(String key) {
        return Boolean.TRUE.equals(redisUtil.redisTemplate.hasKey(key));
    }

    /**
     * @param key key
     */
    public static void extendExpirationTime(String key, long duration) {
        redisUtil.redisTemplate.expire(key, duration, TimeUnit.MILLISECONDS);
    }
}
