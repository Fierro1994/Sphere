package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("refresh_tokens")
public class RefreshToken {
    @Id
    @Indexed
    private Long id;
    @Indexed
    private Long userId;
    @Indexed
    private String token;
    @Indexed
    private Instant expiryDate;

}