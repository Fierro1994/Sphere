package com.example.sphere.security.jwt;

import com.example.sphere.entity.RefreshToken;
import com.example.sphere.exception.RefreshTokenException;
import com.example.sphere.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.lifetime}")
    private Duration refreshExp;

    public RefreshToken createRefreshToken(Long userId){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);
        refreshToken.ifPresent(refreshTokenRepository::delete);
        RefreshToken newRefreshToken = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExp.toMillis()))
                .build();
        return refreshTokenRepository.save(newRefreshToken);
    }

    public Optional<RefreshToken> findByRefreshToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken validateRefreshToken(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.deleteById(refreshToken.getId());
            throw new RefreshTokenException(refreshToken.getToken()+ " Refresh token was expired, repeat signin");
        }
        return refreshToken;
    }

    public Cookie generateRefreshCookie (String refreshToken){
        Cookie cookie = new Cookie("refrcook" , refreshToken);
        cookie.setPath("/api/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int)refreshExp.toSeconds());
        return cookie;
    }

}