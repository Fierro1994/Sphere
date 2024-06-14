package com.example.sphere.service;

import com.example.sphere.entity.Avatar;
import com.example.sphere.entity.ImagePromo;
import com.example.sphere.entity.User;
import com.example.sphere.models.response.UsersInfoRes;
import com.example.sphere.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
public class UsersPagesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFriendsService userFriendsService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public UsersInfoRes getUsersData(String id) {
        Map<String, Object> result = new HashMap<>();
        Optional<User> user = userRepository.findByuserId(id);

        if (user.isPresent()) {
            UsersInfoRes usersInfoRes = new UsersInfoRes();
            usersInfoRes.setAvatar(showAllAvatars(user.get().getUserId()));
            usersInfoRes.setEmail(user.get().getEmail());
            usersInfoRes.setFirstName(user.get().getFirstName());
            usersInfoRes.setLastName(user.get().getLastName());
            usersInfoRes.setInfoModules(user.get().getInfoModules());
            usersInfoRes.setImagePromos(showAllPromo(user.get().getUserId()));
            usersInfoRes.setMomentsList(user.get().getMomentsList());
            usersInfoRes.setListModulesMainPage(user.get().getMainPageModules());
            usersInfoRes.setLastTimeOnline(user.get().getLastTimeOnline());
            usersInfoRes.setUserId(user.get().getUserId());
            usersInfoRes.setUserFriends(userFriendsService.getfriendslist());
            usersInfoRes.setSubscribers(userFriendsService.getSubscriberList());
            usersInfoRes.setSubscriptions(userFriendsService.getSubscribtionsList());
            return usersInfoRes;
        } else {
            return new UsersInfoRes();
        }
    }

    private List<String> showAllAvatars(String userId) {
            User user = userRepository.findByuserId(userId).orElseThrow(() -> {
                log.error("User with userId: {} not found", userId);
                return new UsernameNotFoundException("User with userId: " +userId + " not found");
            });
            List<Avatar> avatarList = user.getAvatar();


            List<String> imageKeys = new ArrayList<>();
            for (Avatar avatar : avatarList){
                String path = "http://localhost:3000/avatar/" + userId + "/" + avatar.getKey() + ".jpg";
                imageKeys.add(path);
            }

            return imageKeys;
    }

    private List<String> showAllPromo(String userId) {
        User user = userRepository.findByuserId(userId).orElseThrow(() -> {
            log.error("User with userId: {} not found", userId);
            return new UsernameNotFoundException("User with userId: " +userId + " not found");
        });
        List<ImagePromo> imagePromos = user.getImagePromos();


        List<String> imageKeys = new ArrayList<>();
        for (ImagePromo imagePromo : imagePromos){
            String path = "http://localhost:3000/imagepromo/" + userId + "/" + imagePromo.getKey() + ".jpg";
            imageKeys.add(path);
        }

        return imageKeys;
    }

}
