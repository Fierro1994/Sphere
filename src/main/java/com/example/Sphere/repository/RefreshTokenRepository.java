package com.example.Sphere.repository;

import com.example.Sphere.entity.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    void deleteByUserId(Long userId);
    Optional<RefreshToken> findByUserId(Long userId);
    void delete (String token);
}
