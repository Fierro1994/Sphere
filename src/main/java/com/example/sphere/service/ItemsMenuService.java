package com.example.sphere.service;

import com.example.sphere.entity.EModulesMenu;
import com.example.sphere.entity.ItemsMenu;
import com.example.sphere.entity.User;
import com.example.sphere.models.request.MenuSettingsAddReq;
import com.example.sphere.models.request.MenuSettingsGetReq;
import com.example.sphere.models.response.MenuSettingsResponse;
import com.example.sphere.repository.ItemsMenuRepository;
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
public class ItemsMenuService {

    @Autowired
    ItemsMenuRepository itemsMenuRepository;
    @Autowired
    UserRepository userRepository;

   public List<ItemsMenu>  setDefaultUserItemsMenu(){
       List<ItemsMenu> itemsMenus = new ArrayList<>();
       itemsMenus.add(new ItemsMenu(EModulesMenu.PROFILE_INFO,true, "Мой профиль"));
       itemsMenus.add(new ItemsMenu(EModulesMenu.MESSAGES, true, "Сообщения"));
       itemsMenus.add(new ItemsMenu(EModulesMenu.FRIENDS, true, "Друзья"));
       itemsMenus.add(new ItemsMenu(EModulesMenu.TREE, true, "Семья"));
       itemsMenus.add(new ItemsMenu(EModulesMenu.GALERY, true, "Галерея"));
       itemsMenus.add(new ItemsMenu(EModulesMenu.MOMENTS, true, "Моменты"));
       itemsMenuRepository.saveAll(itemsMenus);
       return itemsMenus;
    }

    public ResponseEntity<?> menuItemsSetting(@RequestBody MenuSettingsGetReq request)  {
        User user = userRepository.findByuserId(request.getUserId()).get();
        return ResponseEntity.ok()
                .body(new MenuSettingsResponse(user.getItemsMenus().stream().toList()));
    }

    public ResponseEntity<?> updateItemsMenu(@RequestBody MenuSettingsAddReq request){
        User user = userRepository.findByuserId(request.getUserId()).get();


            List<ItemsMenu> itemsMenus = user.getItemsMenus();

            if (Arrays.stream(request.getName()).toList().isEmpty()) {
                itemsMenus.forEach((itemsMenu -> {
                    itemsMenu.setIsEnabled(false);
                }));
            }
            else {
                itemsMenus.forEach((itemsMenu -> {
                    itemsMenu.setIsEnabled(false);
                    Arrays.stream(request.getName()).toList().forEach(elementName -> {
                        if (elementName.equals(itemsMenu.getName().name())) {
                            itemsMenu.setIsEnabled(true);
                        }

                    });
                }));
            }


            user.setItemsMenus(itemsMenus);
            userRepository.save(user);
            return ResponseEntity.ok().body(new MenuSettingsResponse(user.getItemsMenus().stream().toList()));


    }
}
