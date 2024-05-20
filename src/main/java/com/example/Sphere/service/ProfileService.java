package com.example.Sphere.service;


import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.LastTimeOnlineRes;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
