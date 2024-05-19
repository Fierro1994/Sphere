package com.example.Sphere.repository;

import com.example.Sphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByuserId(String userId);
    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled=true WHERE a.email=?1")
    int enableUser(String email);
    Boolean existsByEmail(String email);
}
