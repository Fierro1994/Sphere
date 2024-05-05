package com.example.Sphere.repository;

import com.example.Sphere.entity.Moments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MomentsRepository extends JpaRepository<Moments, Long> {
    Optional<Moments> findBySerialId(String serialId);
}
