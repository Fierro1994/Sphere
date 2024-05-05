package com.example.Sphere.models.response;

import com.example.Sphere.entity.ItemsMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MenuSettingsResponse {
    private List<ItemsMenu> menu;
}
