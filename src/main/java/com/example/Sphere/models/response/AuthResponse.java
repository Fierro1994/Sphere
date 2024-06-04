package com.example.Sphere.models.response;

import com.example.Sphere.entity.Avatar;
import com.example.Sphere.entity.ETheme;
import com.example.Sphere.entity.ItemsMenu;
import com.example.Sphere.entity.MainPageModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Blob;
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

