package com.example.sphere.repository;

import com.example.sphere.entity.Moments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MomentsRepository extends JpaRepository<Moments, Long> {
    Optional<Moments> findByKey(String key);
}
