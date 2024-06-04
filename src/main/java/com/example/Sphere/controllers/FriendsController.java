package com.example.Sphere.controllers;


import java.util.HashMap;
import java.util.Map;

import com.example.Sphere.models.request.*;
import com.example.Sphere.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/friends")
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

public class FriendsController {

    @Autowired
    UserFriendsService userFriendsService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> subscribeUserRequest(@RequestBody SubscribeReq subscribeUserRequestEntity) {
        return userFriendsService.addSubscriptions(subscribeUserRequestEntity);
    }
//
    @RequestMapping(value = "/getsubscriberlist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getsubscriberlist(@RequestBody GetAllIReq GetAllIReq) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("Friends", userFriendsService.getSubscriberList(GetAllIReq.getUserId()));
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/getsubscriptionslist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getsubscriptionslist(@RequestBody GetAllIReq GetAllIReq) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("Friends", userFriendsService.getSubscribtionsList(GetAllIReq.getUserId()));
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getfriendslist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getfriendslist(@RequestBody GetAllIReq GetAllIReq) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("Friends", userFriendsService.getfriendslist(GetAllIReq.getUserId()));
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

}
