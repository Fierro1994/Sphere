package com.example.Sphere.service;

import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.LastTimeOnlineRes;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ResponseEntity<?> setlasttimeonline(String userId) {
        User user = userRepository.findByuserId(userId).get();
        LocalDateTime time = LocalDateTime.now();
        user.setLastTimeOnline(time);
        userRepository.save(user);
        return  ResponseEntity.ok(new LastTimeOnlineRes(time));
    }

    public ResponseEntity<?> getlasttimeonline(String userId) {

       LocalDateTime time =  userRepository.findByuserId(userId).get().getLastTimeOnline();
        return ResponseEntity.ok(new LastTimeOnlineRes(time));

    }
}
