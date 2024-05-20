package com.example.Sphere.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SearchFrResultRes {
    private String userId;
    private String avatar;
    private String firstName;
    private String lastName;
    private LocalDateTime lastTimeOnline;
}
