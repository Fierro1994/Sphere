package com.example.Sphere.service;

import com.example.Sphere.entity.Avatar;
import com.example.Sphere.entity.Gallery;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.GalleryRepos;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
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

    public ResponseEntity<?> showAll(String id) throws IOException {


        User user = userRepository.findByuserId(id).get();
        List<Gallery> galleries = user.getGalleries();
        List<String> imageKeys = new ArrayList<>();
        galleries.forEach(element->{
                imageKeys.add(element.getKey());
        });

        return ResponseEntity.ok().body(imageKeys);
    }
}