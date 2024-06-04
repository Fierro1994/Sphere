package com.example.sphere.models.request;

import com.example.sphere.entity.ETheme;
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
    private String userId;
}
