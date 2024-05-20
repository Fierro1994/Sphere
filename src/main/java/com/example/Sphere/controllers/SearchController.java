package com.example.Sphere.controllers;

import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.models.request.MenuSettingsGetReq;
import com.example.Sphere.repository.UserRepository;
import com.example.Sphere.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/friends/search")
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")
@RequiredArgsConstructor
public class SearchController {
     @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchService searchService;


    @PostMapping ("/allusers")
    public ResponseEntity<?> getAllUsers(@RequestBody GetAllIReq req) throws SQLException, IOException {
        return ResponseEntity.ok(searchService.getAllUsers(req.getUserId()));
    }





}
