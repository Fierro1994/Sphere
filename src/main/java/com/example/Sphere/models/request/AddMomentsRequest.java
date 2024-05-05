package com.example.Sphere.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMomentsRequest {
    private Long userId;
    private String path;
    private String article;
}
