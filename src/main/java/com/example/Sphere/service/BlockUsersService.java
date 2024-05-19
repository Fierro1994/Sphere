
package com.example.Sphere.service;

import java.util.HashMap;
import java.util.Map;

import com.example.Sphere.entity.User;
import com.example.Sphere.models.request.BlockUserRequest;
import com.example.Sphere.repository.UserFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BlockUsersService {

    @Autowired
    private UserFriendsRepository userFriendsRepository;

    public ResponseEntity<Map<String, Object>> addBlockUser(BlockUserRequest blockUserRequestEntity) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (blockUserRequestEntity == null) {
            result.put("Error : ", "Invalid request");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        if (blockUserRequestEntity.getRequestor() == null || blockUserRequestEntity.getTarget() == null) {
            result.put("Error : ", "Requester or Target can not be empty");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        String email1 = blockUserRequestEntity.getRequestor();
        String email2 = blockUserRequestEntity.getTarget();

        if (email1.equals(email2)) {
            result.put("Info : ", "Cannot subscribe , if users are same");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }

        User user1 = this.userFriendsRepository.findByEmail(email1);
        User user2 = this.userFriendsRepository.findByEmail(email2);

        user1.addBlockUsers(user2);
        this.userFriendsRepository.save(user1);

        result.put("Success", true);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

}
