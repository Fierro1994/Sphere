package com.example.sphere.repository;


import com.example.sphere.entity.MainPageModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainModulePageRepos  extends JpaRepository<MainPageModule, Long> {
    Optional<MainPageModule> findByName(String name);
}
