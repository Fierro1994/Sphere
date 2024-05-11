package com.example.Sphere.repository;

import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.InfoModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InfoModRepos  extends JpaRepository<InfoModule, Long> {
    Optional<InfoModule> findByName(String name);

}
