package com.hunt;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;

public class RedisConnectionTest {
    @Test
    public void redisTest() {
        // 替换为你的 Redis URI
        String redisUri = "redis://:123456@localhost:6379/0";
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String response = syncCommands.ping();
        System.out.println("Response: " + response);
        connection.close();
        redisClient.shutdown();
    }
}