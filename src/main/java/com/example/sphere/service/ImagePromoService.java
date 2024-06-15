package com.example.sphere.service;

import com.example.sphere.entity.ImagePromo;
import com.example.sphere.entity.User;
import com.example.sphere.repository.ImagePromoRepos;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class ImagePromoService {
    @Autowired
    ImagePromoRepos imagePromoRepos;
    @Autowired
    FileManager fileManager;
    @Autowired
    UserRepository userRepository;
    private String category = "imagepromo";
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UrlGenerator urlGenerator;

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(MultipartFile file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try {
            fileManager.upload(file,category, null, null);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
       return ResponseEntity.ok().build();
    }


    @Transactional(readOnly = true)
    public ImagePromo findByKey(String key) {
        return imagePromoRepos.findByKey(key).get();
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String key) throws IOException {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();

        User user = userRepository.findByuserId(userDetails.getUserId()).get();
        List<ImagePromo> imagePromos = user.getImagePromos();

        ImagePromo file = imagePromoRepos.findByKey(key).get();

        imagePromos.remove(file);
        user.setImagePromos(imagePromos);
        userRepository.save(user);
        imagePromoRepos.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/"+ user.getUserId() + "/" + category +"/" + key);
            Files.delete(path);
        }catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(element->{
                imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll() {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        if (userDetails != null){
            List<ImagePromo> imagePromos = userDetails.getImagePromos();
            List<String> imageKeys = new ArrayList<>();
            for (ImagePromo imagePromo : imagePromos){
                String path = urlGenerator.generateTemporaryUrl(userDetails.getUserId(), imagePromo.getKey(),imagePromo.getFormat(), category);
                    imageKeys.add(path);
            }
            return ResponseEntity.ok().body(imageKeys);
        }
        else return ResponseEntity.notFound().build();
    }
}