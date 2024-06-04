package com.example.sphere.models.response;

import com.example.sphere.entity.Avatar;
import com.example.sphere.entity.ETheme;
import com.example.sphere.entity.ItemsMenu;
import com.example.sphere.entity.MainPageModule;
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
    private Avatar avatar;
    private String accessToken;
    private String userId;
    private String email;
    private List<ItemsMenu> itemsMenus;
    private List<MainPageModule> listModulesMainPage;
    private ETheme theme;
}

