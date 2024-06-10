package com.example.sphere.models.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @JsonIgnore
    private MultipartFile avatar;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}