package com.example.Sphere.service;


import com.example.Sphere.entity.Gallery;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.GalleryRepos;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepos galleryRepos;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private UserRepository userRepository;
    private String nameFolder = "gallery";

    @Transactional(rollbackFor = {IOException.class})
    public Gallery upload(String file, String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        fileManager.upload(file, userId, nameFolder,key, keySmall);

        Gallery createdFile = new Gallery("gallery",  128, key, keySmall, LocalDateTime.now());
        List<Gallery> galleries = new ArrayList<>();
        Optional<User> user = userRepository.findByuserId(userId);
        if (user.isPresent()){
            galleries = user.get().getGalleries();
            galleries.add(createdFile);
            user.get().setGalleries(galleries);
            galleryRepos.save(createdFile);
            userRepository.save(user.get());
        }
        galleries.add(createdFile);
        galleryRepos.save(createdFile);
        return createdFile;
    }


    public ResponseEntity<Object> download(String id, String key) throws IOException {
        return fileManager.download(id,key,nameFolder);
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String id, String key) throws IOException {
        User user = userRepository.findByuserId(id).get();
        List<Gallery> galleries = user.getGalleries();

        Gallery file = galleryRepos.findByKey(key).get();

        galleries.remove(file);
        user.setGalleries(galleries);
        userRepository.save(user);
        galleryRepos.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
            Files.delete(path);
            Path pathS = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + file.getKeySmall());
            Files.delete(pathS);
        }catch (FileNotFoundException e) {
            System.out.println();
        }

        galleries = user.getGalleries();
        List<String> imageKeys = new ArrayList<>();
        galleries.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll(String id)  {


        User user = userRepository.findByuserId(id).get();
        List<Gallery> galleries = user.getGalleries();
        List<String> imageKeys = new ArrayList<>();
        galleries.forEach(element->{
                imageKeys.add(element.getKey());
        });

        return ResponseEntity.ok().body(imageKeys);
    }
}