package com.example.Sphere.service;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.UsersInfoRes;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersPagesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFriendsService userFriendsService;

    public UsersInfoRes getUsersData(String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional<User> user = userRepository.findByuserId(userId);

        if (user.isPresent()) {
            UsersInfoRes usersInfoRes = new UsersInfoRes();
            usersInfoRes.setAvatar(user.get().getAvatar());
            usersInfoRes.setEmail(user.get().getEmail());
            usersInfoRes.setFirstName(user.get().getFirstName());
            usersInfoRes.setLastName(user.get().getLastName());
            usersInfoRes.setInfoModules(user.get().getInfoModules());
            usersInfoRes.setImagePromos(user.get().getImagePromos());
            usersInfoRes.setMomentsList(user.get().getMomentsList());
            usersInfoRes.setListModulesMainPage(user.get().getMainPageModules());
            usersInfoRes.setLastTimeOnline(user.get().getLastTimeOnline());
            usersInfoRes.setUserId(user.get().getUserId());
            usersInfoRes.setUserFriends(userFriendsService.getfriendslist(user.get().getUserId()));
            usersInfoRes.setSubscribers(userFriendsService.getSubscriberList(user.get().getUserId()));
            usersInfoRes.setSubscriptions(userFriendsService.getSubscribtionsList(user.get().getUserId()));
            return usersInfoRes;
        } else {
            UsersInfoRes usersInfoRes = new UsersInfoRes();
            return usersInfoRes;
        }
    }


}
