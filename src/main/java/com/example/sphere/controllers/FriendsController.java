package com.example.sphere.controllers;


import java.util.HashMap;
import java.util.Map;

import com.example.sphere.models.request.*;
import com.example.sphere.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/friends")
public class FriendsController {

    @Autowired
    UserFriendsService userFriendsService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> subscribeUserRequest(@RequestBody SubscribeReq subscribeUserRequestEntity) {
        return userFriendsService.addSubscriptions(subscribeUserRequestEntity);
    }
    @GetMapping("/getsubscriberlist")
    public ResponseEntity<Map<String, Object>> getsubscriberlist() {
        Map<String, Object> result = new HashMap<>();
        result.put("Subscribers", userFriendsService.getSubscriberList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/getsubscriptionslist")
    public ResponseEntity<Map<String, Object>> getsubscriptionslist() {
        Map<String, Object> result = new HashMap<>();
        result.put("Subscriptions", userFriendsService.getSubscribtionsList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
@GetMapping(value = "/getfriendslist")
    public ResponseEntity<Map<String, Object>> getfriendslist() {
        Map<String, Object> result = new HashMap<>();
        result.put("Friends", userFriendsService.getfriendslist());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
