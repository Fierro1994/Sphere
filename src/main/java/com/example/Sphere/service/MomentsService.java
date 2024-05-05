package com.example.Sphere.service;


import com.example.Sphere.entity.Moments;
import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.repository.MomentsRepository;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MomentsService {

    private final UserRepository userRepository;

    private final MomentsRepository momentsRepository;


    public ResponseEntity<?> addStories(Long userId, String path, String article) {
        User user = userRepository.findById(userId).get();
        List<Moments> momentsList = user.getStories();
        String hashName = UUID.randomUUID().toString();
        momentsList.add(new Moments(hashName, path, article, LocalDateTime.now()));
        user.setStories(momentsList);
        return  ResponseEntity.ok(new SimpleResponse("История добавлена"));
    }


}
