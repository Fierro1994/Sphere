package com.example.Sphere.service;

import java.io.IOException;
import java.util.*;
import com.example.Sphere.entity.User;
import com.example.Sphere.models.request.SubscribeReq;
import com.example.Sphere.models.response.SearchFrResultRes;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    public ResponseEntity<Map<String, Object>> getSubscriberList(String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRepository.findByuserId(userId).get();
        List<SearchFrResultRes> listSubscribers = new ArrayList<>();
        user.getSubscribers().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();

            }

            listSubscribers.add(new SearchFrResultRes(
                    el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));


        });

        result.put("Subscribers", listSubscribers);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }


    public ResponseEntity<Map<String, Object>> getSubscribtionsList(String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRepository.findByuserId(userId).get();
        List<SearchFrResultRes> listSubscriptions = new ArrayList<>();
        user.getSubscriptions().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();

            }


            listSubscriptions.add(new SearchFrResultRes(el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));


        });

        result.put("Subscriptions", listSubscriptions);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getfriendslist(String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRepository.findByuserId(userId).get();
        List<SearchFrResultRes> listFriends = new ArrayList<>();
        user.getFriends().forEach((el) -> {
            String avatar = "";
            if (user.getAvatar().get(0).getName().equals("defavatar")){
                avatar = el.getAvatar().get(0).getName();
            }else {
                avatar = el.getAvatar().get(0).getKey();

            }

            listFriends.add(new SearchFrResultRes(el.getUserId(),
                    avatar,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));


        });

        result.put("Friends", listFriends);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

}
