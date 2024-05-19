package com.example.Sphere.controllers;



import com.example.Sphere.models.request.MainPageModuleAddReq;
import com.example.Sphere.models.request.MainPageModuleGetReq;
import com.example.Sphere.service.MainPageModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/mainpage/settings")
@RequiredArgsConstructor
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
