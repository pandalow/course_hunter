package com.hunt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;

@RedisHash("EmailToken")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailToken implements Serializable {
    @Indexed
    String emailAddress;
    @Id
    String token;
    Instant expiryTime;
    boolean isUsed;
    Instant createdAt;
}
