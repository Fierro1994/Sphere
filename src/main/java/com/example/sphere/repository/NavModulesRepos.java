package com.example.sphere.repository;


import com.example.sphere.entity.NavModules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NavModulesRepos extends JpaRepository<NavModules, Long> {
    Optional<NavModules> findByName(String name);

}
