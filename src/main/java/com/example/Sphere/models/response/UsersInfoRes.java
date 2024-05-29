package com.example.Sphere.models.response;

import com.example.Sphere.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UsersInfoRes {
    private List<Avatar> avatar;
    private String firstName;
    private String lastName;
    private String userId;
    private String email;
    private List<ImagePromo> imagePromos;
    private List<InfoModule> infoModules;
    private List<Moments> momentsList;
    private List<MainPageModule> listModulesMainPage;
    private List<UsersData> userFriends;
    private List<UsersData> subscribers;
    private List<UsersData> subscriptions;
    private LocalDateTime lastTimeOnline;
}

