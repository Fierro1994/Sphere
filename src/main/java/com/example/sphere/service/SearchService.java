package com.example.sphere.service;

import com.example.sphere.entity.Avatar;
import com.example.sphere.entity.User;
import com.example.sphere.models.response.UsersData;
import com.example.sphere.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class SearchService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private final String nameFolder = "avatars";
    public ResponseEntity<?> getAllUsers() {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> {
            log.error("User with userId: {} not found", userDetails.getUserId());
            return new UsernameNotFoundException("User with userId: " + userDetails.getUserId() + " not found");
        });
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
