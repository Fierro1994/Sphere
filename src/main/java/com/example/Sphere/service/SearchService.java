package com.example.Sphere.service;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.response.SearchFrResultRes;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<?> getAllUsers(String userId) throws IOException {
        User user = userRepository.findByuserId(userId).get();
        List<User> userList = userRepository.findAll();
        List<SearchFrResultRes> resultRes = new ArrayList<>();

        userList.forEach((element->{
        if (!element.getUserId().equals(user.getUserId()) && !element.getSubscribers().contains(user) && !element.getSubscriptions().contains(user)){
            Blob blob = element.getAvatar();
            byte[]  blobAsBytes = null;
            int blobLength = 1;
            if(blob!=null){
                try {
                    blobLength = (int) blob.length();
                    blobAsBytes = blob.getBytes(1, blobLength);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            resultRes.add(new SearchFrResultRes(element.getUserId(),
                    blobAsBytes,
                    element.getFirstName(),
                    element.getLastName(),
                    element.getLastTimeOnline()
            ));
        }

        }));
        return ResponseEntity.ok().body(resultRes);
    }

}
