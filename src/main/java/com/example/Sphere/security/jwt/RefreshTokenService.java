package com.example.Sphere.security.jwt;

import com.example.Sphere.entity.RefreshToken;
import com.example.Sphere.repository.RefreshTokenRepository;
import com.example.Sphere.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${jwt.refresh.lifetime}")
    private Long refreshExp;

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByuserId(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExp));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public void  deleteOldRefreshTokenByUserId(Long userId){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId).get();
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+ " Refresh token was expired ");
        }
        return token;
    }

    public Cookie generateRefreshJwtCookie(String refreshToken, int timeExp){
        Cookie cookie = new Cookie("refresh", refreshToken);
        cookie.setMaxAge(timeExp);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/**");
        cookie.setSecure(true);
        return cookie;
    }

}