package com.example.sphere.models.request;

import java.util.List;

public class UserFriendsReq {

    private List<String> friends;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

}