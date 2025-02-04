package com.hunt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // Load Redis host from application configuration
    @Value("${spring.data.redis.host}")
    private String redisHost;

    // Load Redis port from application configuration
    @Value("${spring.data.redis.port}")
    private int redisPort;

    // Load Redis password from application configuration
    @Value("${spring.data.redis.password}")
    private String redisPassword;

    // Load Redis database index from application configuration
    @Value("${spring.data.redis.database}")
    private int redisDatabase;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Create RedisStandaloneConfiguration to configure Redis connection
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(redisPassword);
        config.setDatabase(redisDatabase);

        // Return a new LettuceConnectionFactory with the configuration
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // Create a new RedisTemplate for Redis operations
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Set the serializer for the Redis keys to StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());

        // Set the serializer for the Redis values to GenericJackson2JsonRedisSerializer
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Return the configured RedisTemplate
        return template;
    }
}
