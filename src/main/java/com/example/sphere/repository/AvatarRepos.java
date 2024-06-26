package com.example.sphere.repository;

import com.example.sphere.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AvatarRepos extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByName(String name);
    Optional<Avatar> findByKey(String key);
    Optional<Avatar> findByKeySmall(String keySmall);
}
