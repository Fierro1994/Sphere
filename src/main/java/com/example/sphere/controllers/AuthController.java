package com.example.sphere.controllers;

import com.example.sphere.models.request.CreateUserRequest;
import com.example.sphere.models.request.LoginRequest;
import com.example.sphere.models.request.LogoutRequest;
import com.example.sphere.models.response.RefreshTokenResponse;
import com.example.sphere.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000/", allowCredentials = "true")
public class AuthController {
    @Autowired
    private AuthService authService;



    @PostMapping("/signin")
    public ResponseEntity<?> authResponseResponse(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws SQLException, IOException {
        return authService.authenticateUser(loginRequest, response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserRequest createUserRequest) throws SQLException, IOException {
        return authService.registerUser(createUserRequest);
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestHeader("Authorization") String accessToken, @CookieValue("refrcook") String refreshToken, HttpServletResponse response) {
        return authService.refreshToken(accessToken, refreshToken, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        return authService.logout(logoutRequest);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }
}






