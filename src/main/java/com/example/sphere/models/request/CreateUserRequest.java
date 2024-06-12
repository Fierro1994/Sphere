package com.example.sphere.models.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private MultipartFile avatar;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}