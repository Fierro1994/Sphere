package com.example.sphere.service;

import com.example.sphere.entity.User;
import com.example.sphere.models.response.UsersInfoRes;
import com.example.sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersPagesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFriendsService userFriendsService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public UsersInfoRes getUsersData() {
        Map<String, Object> result = new HashMap<String, Object>();
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        Optional<User> user = userRepository.findById(userDetails.getId());

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
            usersInfoRes.setUserFriends(userFriendsService.getfriendslist());
            usersInfoRes.setSubscribers(userFriendsService.getSubscriberList(user.get().getUserId()));
            usersInfoRes.setSubscriptions(userFriendsService.getSubscribtionsList(user.get().getUserId()));
            return usersInfoRes;
        } else {
            UsersInfoRes usersInfoRes = new UsersInfoRes();
            return usersInfoRes;
        }
    }


}
