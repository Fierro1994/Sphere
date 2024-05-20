package com.example.Sphere.repository;

import com.example.Sphere.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepos extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByName(String name);
    Optional<Avatar> findByKey(String key);
    Optional<Avatar> findByKeySmall(String keySmall);
}
