package com.example.Sphere.repository;

import com.example.Sphere.entity.HeaderAvatar;
import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeaderAvatarRepos  extends JpaRepository<HeaderAvatar, Long> {

    Optional<HeaderAvatar> findByName(String name);
    Optional<HeaderAvatar> findByKey(String key);
}