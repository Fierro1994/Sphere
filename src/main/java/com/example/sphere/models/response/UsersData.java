package com.example.sphere.models.response;

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
public class UsersData {
    private String userId;
    private List<String> avatars;
    private String firstName;
    private String lastName;
    private LocalDateTime lastTimeOnline;
}
