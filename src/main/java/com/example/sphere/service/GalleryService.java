package com.example.sphere.service;


import com.example.sphere.entity.Gallery;
import com.example.sphere.entity.User;
import com.example.sphere.repository.GalleryRepos;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Service
@Slf4j
public class GalleryService {
    @Autowired
    GalleryRepos galleryRepos;
    @Autowired
    FileManager fileManager;
    @Autowired
    UserRepository userRepository;
    String nameFolder = "gallery";

    @Transactional(rollbackFor = {IOException.class})
    public Gallery upload(String file, String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        fileManager.upload(file, userId, nameFolder,key, keySmall);

        Gallery createdFile = new Gallery("gallery", "jpeg",  128L, key, keySmall, LocalDateTime.now());
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
            log.error(e.getMessage(), e);
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