package com.example.Sphere.repository;

import com.example.Sphere.entity.ERole;
import com.example.Sphere.entity.Role;
import com.example.Sphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserFriendsRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserId(String userId);
    List<User> findAllByEmailIn(List<String> emails);
    List<User> findAllByUserIdIn(List<String> userId);
}
