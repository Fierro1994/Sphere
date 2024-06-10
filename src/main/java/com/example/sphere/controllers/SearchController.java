package com.example.sphere.controllers;

import com.example.sphere.models.request.GetAllIReq;
import com.example.sphere.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/friends/search")
@RequiredArgsConstructor
public class SearchController {
    @Autowired
    private SearchService searchService;


    @PostMapping ("/allusers")
    public ResponseEntity<?> getAllUsers(@RequestBody GetAllIReq req) throws IOException {
        return ResponseEntity.ok(searchService.getAllUsers(req.getUserId()));
    }





}
