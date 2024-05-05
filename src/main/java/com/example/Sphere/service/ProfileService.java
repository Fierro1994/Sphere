package com.example.Sphere.service;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.LastTimeOnlineRes;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ResponseEntity<?> setlasttimeonline(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setLastTimeOnline(LocalDateTime.now());
        userRepository.save(user);
        return  ResponseEntity.ok(new SimpleResponse(LocalDateTime.now().toString()));
    }

    public ResponseEntity<?> getlasttimeonline(Long userId) {

       LocalDateTime time =  userRepository.findById(userId).get().getLastTimeOnline();
        return ResponseEntity.ok(new LastTimeOnlineRes(time));

    }
}
