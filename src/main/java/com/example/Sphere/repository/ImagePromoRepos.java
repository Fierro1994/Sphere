package com.example.Sphere.repository;

import com.example.Sphere.entity.ImagePromo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagePromoRepos  extends JpaRepository<ImagePromo, Long> {

    Optional<ImagePromo> findByName(String name);
    Optional<ImagePromo> findByKey(String key);
}