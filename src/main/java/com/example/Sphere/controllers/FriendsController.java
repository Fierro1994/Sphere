package com.example.Sphere.controllers;


import java.util.Map;

import com.example.Sphere.models.request.*;
import com.example.Sphere.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/friends")
public class FriendsController {

    @Autowired
    UserFriendsService userFriendsService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> subscribeUserRequest(@RequestBody SubscribeReq subscribeUserRequestEntity) {
        return this.userFriendsService.addSubscriptions(subscribeUserRequestEntity);
    }
//
    @RequestMapping(value = "/getsubscriberlist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getsubscriberlist(@RequestBody GetAllIReq GetAllIReq) {
        return this.userFriendsService.getSubscriberList(GetAllIReq.getUserId());
    }
    @RequestMapping(value = "/getsubscriptionslist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getsubscriptionslist(@RequestBody GetAllIReq GetAllIReq) {
        return this.userFriendsService.getSubscribtionsList(GetAllIReq.getUserId());
    }

    @RequestMapping(value = "/getfriendslist", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getfriendslist(@RequestBody GetAllIReq GetAllIReq) {
        return this.userFriendsService.getfriendslist(GetAllIReq.getUserId());
    }

}
