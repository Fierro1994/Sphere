package com.example.Sphere.controllers;


import java.util.Map;

import com.example.Sphere.models.request.*;
import com.example.Sphere.service.BlockUsersService;
import com.example.Sphere.service.SubscribeService;
import com.example.Sphere.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/friends")
public class FriendsController {

    @Autowired
    private UserFriendsService userFriendsService;

    @Autowired
    private SubscribeService subscribeService;



    @Autowired
    private BlockUsersService blockUserService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> subscribeUserRequest(@RequestBody SubscribeReq subscribeUserRequestEntity) {
        return this.subscribeService.addSubscribeUser(subscribeUserRequestEntity);
    }


    @RequestMapping(value = "/getUserFriendList", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getUserFriendList(@RequestBody UserFriendsListReq userFriendsListReq) {
        return this.userFriendsService.getUserFriendsList(userFriendsListReq);
    }


    @PostMapping(value = "/getsubscribedusers")
    public ResponseEntity<Map<String, Object>> getSubscribedUsers(@RequestBody GetSubscribedUsersReq req) {
        return this.subscribeService.getSubscribedUser(req);
    }

    @RequestMapping(value = "/getCommonUserFriends", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getCommonUserFriends(@RequestBody UserFriendsReq userFriendsReq) {
        return this.userFriendsService.getCommonUserFriends(userFriendsReq);
    }

    @RequestMapping(value = "/blockUserRequest", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> blockUserRequest(@RequestBody BlockUserRequest blockUserReq) {
        return this.blockUserService.addBlockUser(blockUserReq);
    }
}
