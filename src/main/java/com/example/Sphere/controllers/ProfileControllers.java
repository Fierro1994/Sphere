package com.example.Sphere.controllers;

import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.models.request.LastTimeOnline;
import com.example.Sphere.models.request.MenuSettingsAddReq;
import com.example.Sphere.models.request.MenuSettingsGetReq;
import com.example.Sphere.service.ItemsMenuService;
import com.example.Sphere.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/profile/settings")
@CrossOrigin(value ="http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class ProfileControllers {
    private final ItemsMenuService settingsService;
    private final ProfileService profileService;


    @PostMapping("/getmenuelement")
    public ResponseEntity<?> getmenuelement(@RequestBody MenuSettingsGetReq request) throws SQLException {
        return ResponseEntity.ok(settingsService.menuItemsSetting(request));
    }

    @PostMapping("/updatemenuelement")
    public ResponseEntity<?> updatemenuelement(@RequestBody MenuSettingsAddReq request) {
        return ResponseEntity.ok(settingsService.updateItemsMenu(request));
    }

    @PostMapping("/setlasttimeonline")
    public ResponseEntity<?> setlasttimeonline(@RequestBody LastTimeOnline request) {

        return ResponseEntity.ok( profileService.setlasttimeonline(request.getUserId()));
    }

    @PostMapping("/getlasttimeonline")
    public ResponseEntity<?> getlasttimeonline(@RequestBody LastTimeOnline request) {
        return ResponseEntity.ok( profileService.getlasttimeonline(request.getUserId()));

    }

}
