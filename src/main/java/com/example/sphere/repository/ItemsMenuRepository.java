package com.example.sphere.repository;

import com.example.sphere.entity.ItemsMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ItemsMenuRepository extends JpaRepository<ItemsMenu, Long> {
    Optional<ItemsMenu> findByName(String name);

}
