package com.example.Sphere.redis.event;

import com.example.Sphere.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RedisExpirationEvent {
    @EventListener
    public void RedisExpirationHandler(RedisKeyExpiredEvent<RefreshToken> event) {
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();
        if (expiredRefreshToken == null) {
            throw  new RuntimeException("Refresh token is null");
        }
        log.info("Refresh token {}, with key = {} expired", expiredRefreshToken.getToken(), expiredRefreshToken.getId());
    }
}
