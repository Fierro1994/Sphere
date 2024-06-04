package com.example.sphere.repository;

import com.example.sphere.entity.InfoModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InfoModRepos  extends JpaRepository<InfoModule, Long> {
    Optional<InfoModule> findByName(String name);

}
