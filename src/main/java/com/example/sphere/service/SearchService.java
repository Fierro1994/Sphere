package com.example.sphere.service;

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

    private String nameFolder = "avatars";
    public ResponseEntity<?> getAllUsers(String userId) throws IOException {
        User user = userRepository.findByuserId(userId).get();
        List<User> userList = userRepository.findAll();
        List<UsersData> resultRes = new ArrayList<>();

        userList.forEach((element->{
        if (!element.getUserId().equals(user.getUserId()) && !element.getSubscribers().contains(user) && !element.getSubscriptions().contains(user) &&!element.getFriends().contains(user) && !user.getFriends().contains(element)){

            String avatar = "";
            if (element.getAvatar().get(0).getName().equals("defavatar.jpg")){
                avatar = element.getAvatar().get(0).getName();
            }else {
                avatar = element.getAvatar().get(0).getKeySmall();
            }
            resultRes.add(new UsersData(element.getUserId(),
                    avatar,
                    element.getFirstName(),
                    element.getLastName(),
                    element.getLastTimeOnline()
            ));
        }

        }));
        return ResponseEntity.ok().body(resultRes);
    }

}
