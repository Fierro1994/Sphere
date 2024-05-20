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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ResponseEntity<?> setlasttimeonline(String userId) {
       Optional<User> user = userRepository.findByuserId(userId);
       if (user.isPresent()){
           LocalDateTime time = LocalDateTime.now();
           user.get().setLastTimeOnline(time);
           userRepository.save(user.get());
           return  ResponseEntity.ok(new LastTimeOnlineRes(time));
       }else {
           return ResponseEntity.ok(new SimpleResponse("User не найден"));
       }

    }

    public ResponseEntity<?> getlasttimeonline(String userId) {
        Optional<User> user = userRepository.findByuserId(userId);
        if (user.isPresent()){LocalDateTime time =  user.get().getLastTimeOnline();
        return ResponseEntity.ok(new LastTimeOnlineRes(time));
        }
        else   return ResponseEntity.ok(new SimpleResponse("User не найден"));

    }
}
