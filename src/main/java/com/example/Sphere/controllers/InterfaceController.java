package com.example.Sphere.controllers;

import com.example.Sphere.models.request.ThemeSelectGetReq;
import com.example.Sphere.models.request.ThemeSelectReq;
import com.example.Sphere.service.InterfaceService;
import com.example.Sphere.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/settings/interface")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

public class InterfaceController {

    private final InterfaceService interfaceService;
    private final ProfileService profileService;

    @PostMapping("/selecttheme")
    public ResponseEntity<?> selecttheme(@RequestBody ThemeSelectReq request) {
        return ResponseEntity.ok(interfaceService.themeSelect(request));
    }
    @PostMapping("/getselecttheme")
    public ResponseEntity<?> getselecttheme(@RequestBody ThemeSelectGetReq request) {
        return ResponseEntity.ok(interfaceService.getThemeSelect(request));
    }

}
