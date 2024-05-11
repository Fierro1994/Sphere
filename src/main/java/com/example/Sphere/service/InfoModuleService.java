package com.example.Sphere.service;

import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.InfoModule;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.HeaderAvatarRepos;
import com.example.Sphere.repository.InfoModRepos;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfoModuleService {
    @Autowired
    private InfoModRepos infoModRepos;
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = {IOException.class})
    public List<InfoModule> setDefaultInfo(Long userId) throws IOException {
        User user = userRepository.findById(userId).get();
        List<InfoModule> infoModules = user.getInfoModules();
        InfoModule defInfoModule1 = new InfoModule();
        defInfoModule1.setName("Адрес");
        defInfoModule1.setInfotext("Промо 9");
        defInfoModule1.setBlock(1);
        InfoModule defInfoModule2 = new InfoModule();
        defInfoModule2.setName("Телефон");
        defInfoModule2.setInfotext("999-999");
        defInfoModule2.setBlock(2);
        infoModules.add(defInfoModule1);
        infoModules.add(defInfoModule2);
        user.setInfoModules(infoModules);
        infoModRepos.saveAll(infoModules);
        userRepository.save(user);
        return infoModules;
    }

    public ResponseEntity<?> getAll(Long id) throws IOException {

        User user = userRepository.findById(id).get();
        List<InfoModule> infoModules = user.getInfoModules();
        return ResponseEntity.ok().body(infoModules);
    }


}
