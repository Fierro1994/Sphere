package com.example.Sphere.service;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.request.ThemeSelectGetReq;
import com.example.Sphere.models.request.ThemeSelectReq;
import com.example.Sphere.models.response.ThemeSelectRes;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class InterfaceService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> themeSelect(@RequestBody ThemeSelectReq request)  {
        User user = userRepository.findById(request.getUserId()).get();
        user.setThemes(request.getName());
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(new ThemeSelectRes(user.getThemes().name()));
    }

    public ResponseEntity<?> getThemeSelect(@RequestBody ThemeSelectGetReq request)  {
        User user = userRepository.findById(request.getUserId()).get();
        return ResponseEntity.ok()
                .body(new ThemeSelectRes(user.getThemes().name()));
    }
}
