package com.example.Sphere.service;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.request.SubscribeReq;
import com.example.Sphere.models.response.SearchFrResultRes;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserFriendsService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Map<String, Object>> addSubscriptions(SubscribeReq subscribeReq) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRepository.findByuserId(subscribeReq.getRequestor()).get();
        User toUser = userRepository.findByuserId(subscribeReq.getTarget()).get();
        if (user.getSubscribers().contains(toUser)){
            user.getSubscribers().remove(toUser);
            user.getFriends().add(toUser);
            userRepository.save(user);
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

            Blob blob = el.getAvatar();
            byte[] blobAsBytes = null;
            int blobLength = 1;
            if (blob != null) {
                try {
                    blobLength = (int) blob.length();
                    blobAsBytes = blob.getBytes(1, blobLength);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            listSubscribers.add(new SearchFrResultRes(el.getUserId(),
                    blobAsBytes,
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

            Blob blob = el.getAvatar();
            byte[] blobAsBytes = null;
            int blobLength = 1;
            if (blob != null) {
                try {
                    blobLength = (int) blob.length();
                    blobAsBytes = blob.getBytes(1, blobLength);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            listSubscriptions.add(new SearchFrResultRes(el.getUserId(),
                    blobAsBytes,
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

            Blob blob = el.getAvatar();
            byte[] blobAsBytes = null;
            int blobLength = 1;
            if (blob != null) {
                try {
                    blobLength = (int) blob.length();
                    blobAsBytes = blob.getBytes(1, blobLength);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            listFriends.add(new SearchFrResultRes(el.getUserId(),
                    blobAsBytes,
                    el.getFirstName(),
                    el.getLastName(),
                    el.getLastTimeOnline()
            ));


        });

        result.put("Friends", listFriends);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }




}
