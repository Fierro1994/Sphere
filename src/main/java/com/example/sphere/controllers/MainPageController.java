package com.example.sphere.controllers;



import com.example.sphere.models.request.MainPageModuleAddReq;
import com.example.sphere.models.request.MainPageModuleGetReq;
import com.example.sphere.service.MainPageModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/mainpage/settings")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

public class MainPageController {
    private final MainPageModuleService mainPageModuleService;

    @PostMapping("/getmodule")
    public ResponseEntity<?> getmodule(@RequestBody MainPageModuleGetReq request) throws SQLException {
        return ResponseEntity.ok(mainPageModuleService.mainPageModulesGet(request));
    }

    @PostMapping("/updatemodule")
    public ResponseEntity<?> updatemodule(@RequestBody MainPageModuleAddReq request) {
        return ResponseEntity.ok(mainPageModuleService.updateMainPageModule(request));
    }


}
