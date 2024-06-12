package com.example.sphere.service;

import com.example.sphere.entity.*;
import com.example.sphere.models.request.MenuSettingsAddReq;
import com.example.sphere.models.request.MenuSettingsGetReq;
import com.example.sphere.repository.NavModulesRepos;
import com.example.sphere.repository.UserRepository;
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
public class NavModuleService {

        @Autowired
        NavModulesRepos navModulesRepos;
        @Autowired
        UserRepository userRepository;

        public List<NavModules> setDefaultNavModules(){
            List<NavModules> navModules = new ArrayList<>();
            navModules.add(new NavModules(ENavModules.MY_NEWS,true, "Моя лента"));
            navModules.add(new NavModules(ENavModules.MY_CONTENT,true, "Мой контент"));
            navModules.add(new NavModules(ENavModules.MY_SHOP,true, "Мой магазин"));
            navModulesRepos.saveAll(navModules);
            return navModules;
        }

        public ResponseEntity<?> navModulesList(@RequestBody MenuSettingsGetReq request)  {
            User user = userRepository.findByuserId(request.getUserId()).get();
            return ResponseEntity.ok()
                    .body(user.getNavItems().stream().toList());
        }

        public ResponseEntity<?> updateNavModules(@RequestBody MenuSettingsAddReq request){
            User user = userRepository.findByuserId(request.getUserId()).get();


            List<NavModules> navModules = user.getNavItems();

            if (Arrays.stream(request.getName()).toList().isEmpty()) {
                navModules.forEach((el -> {
                    el.setIsEnabled(false);
                }));
            }
            else {
                navModules.forEach((el -> {
                    el.setIsEnabled(false);
                    Arrays.stream(request.getName()).toList().forEach(elementName -> {
                        if (elementName.equals(el.getName().name())) {
                            el.setIsEnabled(true);
                        }

                    });
                }));
            }


            user.setNavItems(navModules);
            userRepository.save(user);
            return ResponseEntity.ok().body(user.getNavItems().stream().toList());


        }


}
