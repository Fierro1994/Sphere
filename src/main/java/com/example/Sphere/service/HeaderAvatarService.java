package com.example.Sphere.service;

import com.example.Sphere.entity.HeaderAvatar;
import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.HeaderAvatarRepos;
import com.example.Sphere.repository.ImagePromoRepos;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HeaderAvatarService {
    @Autowired
    private HeaderAvatarRepos headerAvatarRepos;
    @Autowired
    private UserRepository userRepository;
    private String nameFolder = "header";

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(String file, String name, Long size, String userId) throws IOException {
        String key = UUID.randomUUID().toString();

        HeaderAvatar createdFile = new HeaderAvatar(name, size, key, LocalDateTime.now());
        User user = userRepository.findByuserId(userId).get();
        List<HeaderAvatar> headerAvatars = user.getHeaderAvatars();

        Optional<HeaderAvatar> defhead = headerAvatarRepos.findByName("defheadavatar");

        if (defhead.isPresent()){
            if (headerAvatars.contains(defhead.get())){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + defhead.get().getName());
                headerAvatars.remove(defhead);
                headerAvatarRepos.delete(defhead.get());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + defhead.get().getName() + " not found");
                }
            }

        }


        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        convertFile.createNewFile();
        String base64ImageString = file.replace("data:image/jpeg;base64,", "");
        byte[] result = Base64.getDecoder().decode(base64ImageString);
        OutputStream out = new FileOutputStream(convertFile);
        out.write(result);
        out.close();

        headerAvatars.add(createdFile);

        user.setHeaderAvatars(headerAvatars);

        headerAvatarRepos.save(createdFile);
        userRepository.save(user);

        headerAvatars = user.getHeaderAvatars();
        List<String> imageKeys = new ArrayList<>();
        headerAvatars.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);

    }

    @Transactional(rollbackFor = {IOException.class})
    public List<HeaderAvatar> defaultUpload(String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        FileInputStream img = new FileInputStream("src/main/resources/header/defheadavatar.jpg");
        HeaderAvatar createdFile = new HeaderAvatar("defheadavatar",122L, key, LocalDateTime.now());



        List<HeaderAvatar> headerAvatars = new ArrayList<>();
        headerAvatars.add(createdFile);

        try {
            Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "defheadavatar");
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


        headerAvatarRepos.saveAll(headerAvatars);
        return headerAvatars;

    }



    public ResponseEntity<Object> download(String id, String key) throws IOException {
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" +nameFolder +"/" + key);
        File file = new File(path.toUri());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        Resource resource2 = new UrlResource(path.toUri());
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }

    @Transactional(readOnly = true)
    public HeaderAvatar findByKey(String key) {
        return headerAvatarRepos.findByKey(key).get();
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String id, String key) throws IOException {
        User user = userRepository.findByuserId(id).get();
        List<HeaderAvatar> headerAvatars = user.getHeaderAvatars();

        HeaderAvatar file = headerAvatarRepos.findByKey(key).get();

        headerAvatars.remove(file);
        user.setHeaderAvatars(headerAvatars);
        userRepository.save(user);
        headerAvatarRepos.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
            Files.delete(path);
        }catch (FileNotFoundException e) {
            System.out.println();
        }

        headerAvatars = user.getHeaderAvatars();
        List<String> imageKeys = new ArrayList<>();
        headerAvatars.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll(String id) throws IOException {

        User user = userRepository.findByuserId(id).get();
        List<HeaderAvatar> headerAvatars = user.getHeaderAvatars();
        List<String> imageKeys = new ArrayList<>();
        headerAvatars.forEach(element->{
            if(element.getName().equals("defheadavatar")){
                imageKeys.add(element.getName());
            }
            else{
                imageKeys.add(element.getKey());
            }
        });

        return ResponseEntity.ok().body(imageKeys);
    }
}