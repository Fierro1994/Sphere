package com.example.sphere.service;

import com.example.sphere.entity.InfoModule;
import com.example.sphere.entity.User;
import com.example.sphere.repository.InfoModRepos;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoModuleService {
    @Autowired
    private InfoModRepos infoModRepos;
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = {IOException.class})
    public List<InfoModule> setDefaultInfo(String userId) throws IOException {
        User user = userRepository.findByuserId(userId).get();
        List<InfoModule> infoModules = new ArrayList<>();
        System.out.println(user.getInfoModules());
        InfoModule defInfoModule1 = new InfoModule();
        defInfoModule1.setName("Адрес: для примера");
        defInfoModule1.setBlock(1);
        InfoModule defInfoModule2 = new InfoModule();
        defInfoModule2.setName("e-mail: для примера");
        defInfoModule2.setBlock(2);
        InfoModule defInfoModule3 = new InfoModule();
        defInfoModule3.setName("Телефон: для примера");
        defInfoModule3.setBlock(3);
        InfoModule defInfoModule4 = new InfoModule();
        defInfoModule4.setName("Рабочий адрес: для примера");
        defInfoModule4.setBlock(4);
        InfoModule defInfoModule5 = new InfoModule();
        defInfoModule5.setName("Производство: для примера");
        defInfoModule5.setBlock(5);
        InfoModule defInfoModule6 = new InfoModule();
        defInfoModule6.setName("Общая информация: для примера");
        defInfoModule6.setBlock(6);
        infoModules.add(defInfoModule1);
        infoModules.add(defInfoModule2);
        infoModules.add(defInfoModule3);
        infoModules.add(defInfoModule4);
        infoModules.add(defInfoModule5);
        infoModules.add(defInfoModule6);
        user.setInfoModules(infoModules);
        infoModRepos.saveAll(infoModules);
        userRepository.save(user);
        return infoModules;
    }

    public ResponseEntity<?> getAll(String id) throws IOException {

        User user = userRepository.findByuserId(id).get();
        List<InfoModule> infoModules = user.getInfoModules();
        return ResponseEntity.ok().body(infoModules);
    }

    public ResponseEntity<?> upload(String id, List<InfoModule> infoModulesReq) throws IOException {
        User user = userRepository.findByuserId(id).get();
        List<InfoModule> infoModules = user.getInfoModules();
        infoModules.forEach((el->{
            infoModulesReq.forEach((el2->{

                if (el2.getBlock() == el.getBlock()){
                    el.setName(el2.getName());
                }
            }));
        }));
        user.setInfoModules(infoModules);
        userRepository.save(user);
        return ResponseEntity.ok().body(infoModules);
    }

}
