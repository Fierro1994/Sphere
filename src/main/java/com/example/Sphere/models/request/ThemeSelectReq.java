package com.example.Sphere.models.request;

import com.example.Sphere.entity.ETheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeSelectReq {
    private ETheme name;
    private Long userId;
}
