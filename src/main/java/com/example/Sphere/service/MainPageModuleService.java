package com.example.Sphere.service;

import com.example.Sphere.entity.*;
import com.example.Sphere.models.request.MainPageModuleAddReq;
import com.example.Sphere.models.request.MainPageModuleGetReq;
import com.example.Sphere.models.response.MainPageModuleResponse;
import com.example.Sphere.repository.MainModulePageRepos;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageModuleService {
    @Autowired
    MainModulePageRepos mainModulePageRepos;
    @Autowired
    UserRepository userRepository;

    public List<MainPageModule> setDefaultUserPageModule(){

        List<MainPageModule> pageModules = new ArrayList<>();
        pageModules.add(new MainPageModule(EMainPageModules.HEADER,true, "Верхний блок"));
        pageModules.add(new MainPageModule(EMainPageModules.NAVIGATION,true, "Навигация"));
        pageModules.add(new MainPageModule(EMainPageModules.ACTUAL,true, "Актуальное"));
        pageModules.add(new MainPageModule(EMainPageModules.CARTS,true, "Инфо-блоки"));
        pageModules.add(new MainPageModule(EMainPageModules.CONTENT,true, "Контент"));
        pageModules.add(new MainPageModule(EMainPageModules.INFO,true, "Информация"));
        pageModules.add(new MainPageModule(EMainPageModules.PROMO,true, "Промо-блок"));
        mainModulePageRepos.saveAll(pageModules);
        return pageModules;
    }

    public ResponseEntity<?> mainPageModulesGet(@RequestBody MainPageModuleGetReq request)  {
        User user = userRepository.findByuserId(request.getUserId()).get();
        return ResponseEntity.ok()
                .body(new MainPageModuleResponse(user.getMainPageModules().stream().toList()));
    }

    public ResponseEntity<?> updateMainPageModule(@RequestBody MainPageModuleAddReq request){
        User user = userRepository.findByuserId(request.getUserId()).get();

            List<MainPageModule> mainPageModules = user.getMainPageModules();

        mainPageModules.forEach((mainPageModule -> {
            mainPageModule.setIsEnabled(false);
                Arrays.stream(request.getName()).toList().forEach(elementName -> {
                    if (elementName.equals(mainPageModule.getName().name())) {
                        mainPageModule.setIsEnabled(true);
                    }
                });
            }));
            user.setMainPageModules(mainPageModules);
            userRepository.save(user);
            return ResponseEntity.ok().body(new MainPageModuleResponse(user.getMainPageModules().stream().toList()));

    }
}
