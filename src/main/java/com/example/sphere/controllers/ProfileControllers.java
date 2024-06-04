package com.example.sphere.controllers;

import com.example.sphere.models.request.LastTimeOnline;
import com.example.sphere.models.request.MenuSettingsAddReq;
import com.example.sphere.models.request.MenuSettingsGetReq;
import com.example.sphere.service.ItemsMenuService;
import com.example.sphere.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/profile/settings")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

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
