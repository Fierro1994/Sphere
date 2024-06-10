package com.example.sphere.controllers;

import com.example.sphere.models.request.ThemeSelectGetReq;
import com.example.sphere.models.request.ThemeSelectReq;
import com.example.sphere.service.InterfaceService;
import com.example.sphere.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/settings/interface")
@RequiredArgsConstructor

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
