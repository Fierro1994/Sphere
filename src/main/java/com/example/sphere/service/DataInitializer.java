package com.example.sphere.service;

import com.example.sphere.entity.ERole;
import com.example.sphere.entity.Role;
import com.example.sphere.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role role1 = new Role();
            role1.setName(ERole.ROLE_USER);
            roleRepository.save(role1);

            Role role2 = new Role();
            role2.setName(ERole.ROLE_ADMIN);
            roleRepository.save(role2);

            Role role3 = new Role();
            role2.setName(ERole.ROLE_MODERATOR);
            roleRepository.save(role3);
        }
    }
}