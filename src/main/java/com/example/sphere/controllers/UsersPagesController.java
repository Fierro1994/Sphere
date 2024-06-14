package com.example.sphere.controllers;
import com.example.sphere.service.UsersPagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/friends")
@RequiredArgsConstructor

public class UsersPagesController {
    @Autowired
    UsersPagesService usersPagesService;

    @GetMapping("/getusersdata/{id}")
    public ResponseEntity<Map<String, Object>>  getmenuelement(@PathVariable("id") String id) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        System.out.println(id);
        result.put("UserData", usersPagesService.getUsersData(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
