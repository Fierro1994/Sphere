package com.example.sphere.service;

import com.example.sphere.entity.Avatar;
import com.example.sphere.entity.User;
import com.example.sphere.models.response.UsersData;
import com.example.sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private String nameFolder = "avatars";
    public ResponseEntity<?> getAllUsers() throws IOException {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        User user = userRepository.findById(userDetails.getId()).get();
        List<User> userList = userRepository.findAll();
        List<UsersData> resultRes = new ArrayList<>();

        userList.forEach((element->{
        if (!element.getId().equals(user.getId()) && !element.getSubscribers().contains(user) && !element.getSubscriptions().contains(user) &&!element.getFriends().contains(user) && !user.getFriends().contains(element)){
            List<Avatar> avatars = new ArrayList<>();
            List<String> imageKeys = new ArrayList<>();

            if (!element.getAvatar().isEmpty()){
                avatars = element.getAvatar();

                for (Avatar avatar : avatars){
                    String path = "http://localhost:3000/avatar/" + element.getUserId() + "/" + avatar.getKey() + ".jpg";
                    imageKeys.add(path);
                }
            }
            resultRes.add(new UsersData(element.getUserId(),
                    imageKeys,
                    element.getFirstName(),
                    element.getLastName(),
                    element.getLastTimeOnline()
            ));
        }

        }));
        return ResponseEntity.ok().body(resultRes);
    }

}
