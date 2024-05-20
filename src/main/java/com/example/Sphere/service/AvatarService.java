package com.example.Sphere.service;

import com.example.Sphere.entity.Avatar;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.AvatarRepos;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepos avatarRepos;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileManager fileManager;
    private String nameFolder = "avatars";

    @Transactional(rollbackFor = {IOException.class})
    public Avatar upload(String file,String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        fileManager.upload(file, userId, nameFolder,key, keySmall);

        Avatar createdFile = new Avatar("avatar",  128, key, keySmall, LocalDateTime.now());
        List<Avatar> avatars = new ArrayList<>();
        Optional<User> user = userRepository.findByuserId(userId);
        if (user.isPresent()){
            avatars = user.get().getAvatar();
            avatars.add(createdFile);
            user.get().setAvatar(avatars);
            avatarRepos.save(createdFile);
            userRepository.save(user.get());
        }

        Optional<Avatar> defAva = avatarRepos.findByName("defAvatar");

        if (defAva.isPresent()){
            if (avatars.contains(defAva.get())){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + defAva.get().getName());
                avatars.remove(defAva);
                avatarRepos.delete(defAva.get());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + defAva.get().getName() + " not found");
                }
            }

        }
        avatars.add(createdFile);
        avatarRepos.save(createdFile);
        return createdFile;
    }

    @Transactional(rollbackFor = {IOException.class})
    public Avatar defaultUpload(String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        FileInputStream img = new FileInputStream("src/main/resources/avatars/defavatar.jpg");
        Avatar createdFile = new Avatar("defavatar",122, key, LocalDateTime.now());
        List<Avatar> avatars = new ArrayList<>();
        avatars.add(createdFile);

        try {
            Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "defavatar");
            File directory = new File(path.getParent().toString());

            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileOutputStream fileOut = new FileOutputStream(path.toString());
            while (img.available() > 0) {
                int oneByte = img.read();
                fileOut.write(oneByte);
            }
            img.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        avatarRepos.saveAll(avatars);
        return createdFile;

    }



    public ResponseEntity<Object> download(String id, String key) throws IOException {
       return fileManager.download(id,key,nameFolder);
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String id, String key) throws IOException {
        User user = userRepository.findByuserId(id).get();
        List<Avatar> avatars = user.getAvatar();

        Avatar file = avatarRepos.findByKey(key).get();

        avatars.remove(file);
        user.setAvatar(avatars);
        userRepository.save(user);
        avatarRepos.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
            Files.delete(path);
            Path pathS = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + file.getKeySmall());
            Files.delete(pathS);
        }catch (FileNotFoundException e) {
            System.out.println();
        }

        avatars = user.getAvatar();
        List<String> imageKeys = new ArrayList<>();
        avatars.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll(String id) throws IOException {


        User user = userRepository.findByuserId(id).get();
        List<Avatar> avatars = user.getAvatar();
        List<String> imageKeys = new ArrayList<>();
        avatars.forEach(element->{
            if(element.getName().equals("defavatar")){
                imageKeys.add(element.getName());
            }
            else{
                imageKeys.add(element.getKey());
            }
        });

        return ResponseEntity.ok().body(imageKeys);
    }
}