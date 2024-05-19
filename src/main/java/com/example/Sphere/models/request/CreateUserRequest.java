package com.example.Sphere.models.request;

import lombok.*;

import java.sql.Blob;
import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    private String avatar;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}