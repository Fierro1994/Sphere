package com.example.Sphere.repository;

import com.example.Sphere.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GalleryRepos  extends JpaRepository<Gallery, Long> {

    Optional<Gallery> findByName(String name);
    Optional<Gallery> findByKey(String key);
}