package com.example.sphere.service;

import java.io.IOException;
import java.util.*;
import com.example.sphere.entity.User;
import com.example.sphere.models.request.SubscribeReq;
import com.example.sphere.models.response.UsersData;
import com.example.sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFriendsService {

    @Autowired
    UserRepository userRepository;


    private String nameFolder = "avatars";
    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<Map<String, Object>> addSubscriptions(SubscribeReq subscribeReq) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRepository.findByuserId(subscribeReq.getRequestor()).get();
        User toUser = userRepository.findByuserId(subscribeReq.getTarget()).get();
        if (user.getSubscribers().contains(toUser)){
            user.getSubscribers().remove(toUser);
            user.getFriends().add(toUser);
            userRepository.save(user);
            toUser.getFriends().add(user);
            userRepository.save(toUser);
            result.put("Добавлен в друзья!", null);
            return ResponseEntity.ok(result);
        }
        else {
            user.getSubscriptions().add(toUser);
            userRepository.save(user);
            result.put("Заявка отпправлена!", null);

            return ResponseEntity.ok(result);
        }


    }


    public List<UsersData>  getSubscriberList(String userId) {
        User user = userRepository.findByuserId(userId).get();
        List<UsersData> listSubscribers = new ArrayList<>();
        user.getSubscribers().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar.jpg")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();
            }
            listSubscribers.add(new UsersData(
                    el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));
        });
        return listSubscribers;
    }

    public List<UsersData> getSubscribtionsList(String userId) {
        User user = userRepository.findByuserId(userId).get();
        List<UsersData> listSubscriptions = new ArrayList<>();
        user.getSubscriptions().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar.jpg")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();

            }
            listSubscriptions.add(new UsersData(el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));
        });
        return listSubscriptions;
    }

    public List<UsersData> getfriendslist(String userId) {
        User user = userRepository.findByuserId(userId).get();
        List<UsersData> listFriends = new ArrayList<>();
        user.getFriends().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar.jpg")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();
            }
            listFriends.add(new UsersData(el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));
        });
       return listFriends;
    }

}
