package com.example.Sphere.service;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.request.GetSubscribedUsersReq;
import com.example.Sphere.models.request.SubscribeReq;
import com.example.Sphere.repository.UserFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscribeService {
    @Autowired
    private UserFriendsRepository userFriendsRepository;


      public ResponseEntity<Map<String, Object>> addSubscribeUser(SubscribeReq subscribeUserRequestEntity) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (subscribeUserRequestEntity == null) {
            result.put("Error : ", "Invalid request");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        if (subscribeUserRequestEntity.getRequestor() == null || subscribeUserRequestEntity.getTarget() == null) {
            result.put("Error : ", "Requester or Target can not be empty");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        String id1 = subscribeUserRequestEntity.getRequestor();
        String id2 = subscribeUserRequestEntity.getTarget();

        if (id1.equals(id2)) {
            result.put("Info : ", "Cannot subscribe , if users are same");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }

        User user1 = this.userFriendsRepository.findByUserId(id1);
        User user2 = this.userFriendsRepository.findByUserId(id2);

        user1.addSubscribeUsers(user2);
        this.userFriendsRepository.save(user1);

        result.put("Success", true);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getSubscribedUser(GetSubscribedUsersReq subscribeUserRequestEntity) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (subscribeUserRequestEntity == null) {
            result.put("Error : ", "Invalid request");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        if (subscribeUserRequestEntity.getUserId() == null) {
            result.put("Error : ", "Requester or Target can not be empty");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        String id1 = subscribeUserRequestEntity.getUserId();


        User user1 = this.userFriendsRepository.findByUserId(id1);
        Set<User> subscribedUsers = user1.getUserFriends();
        this.userFriendsRepository.save(user1);

        result.put("Success", subscribedUsers);
        System.out.println(user1.getSubscribeUsers());
        return ResponseEntity.ok().body(result);
    }
}
