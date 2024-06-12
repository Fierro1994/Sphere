package com.example.sphere.models.response;

import com.example.sphere.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AuthResponse {
    private List<Avatar> avatars;
    private String accessToken;
    private String userId;
    private String email;
    private List<ItemsMenu> itemsMenus;
    private List<MainPageModule> listModulesMainPage;
    private List<NavModules> navModules;
    private ETheme theme;
}

