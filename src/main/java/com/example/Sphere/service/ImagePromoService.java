package com.example.Sphere.service;

import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
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
public class ImagePromoService {
    @Autowired
    private ImagePromoRepos imagePromoRepos;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private UserRepository userRepository;
    private String nameFolder = "imagepromo";

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(String file, String name, Long size, Long userId) throws IOException {
        String key = UUID.randomUUID().toString();

        ImagePromo createdFile = new ImagePromo(name, size, key, LocalDateTime.now());
        User user = userRepository.findById(userId).get();
        List<ImagePromo> imagePromos = user.getImagePromos();

        Optional<ImagePromo> defpromo = imagePromoRepos.findByName("promo_1");
        Optional<ImagePromo> defpromo2 = imagePromoRepos.findByName("promo_2");
        Optional<ImagePromo> defpromo3 = imagePromoRepos.findByName("promo_3");

        if (defpromo.isPresent()){
            if (imagePromos.contains(defpromo.get())){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + defpromo.get().getName());
                imagePromos.remove(defpromo);
                imagePromoRepos.delete(defpromo.get());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + defpromo.get().getName() + " not found");
                }
            }
            if (imagePromos.contains(defpromo2.get())){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + defpromo2.get().getName());
                imagePromos.remove(defpromo2);
                imagePromoRepos.delete(defpromo2.get());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + defpromo2.get().getName() + " not found");
                }
            }
            if (imagePromos.contains(defpromo3.get())){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + defpromo3.get().getName());
                imagePromos.remove(defpromo3);
                imagePromoRepos.delete(defpromo3.get());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + defpromo3.get().getName() + " not found");
                }
            }


        }


        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdir();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        convertFile.createNewFile();
        String base64ImageString = file.replace("data:image/jpeg;base64,", "");
        byte[] result = Base64.getDecoder().decode(base64ImageString);
        OutputStream out = new FileOutputStream(convertFile);
        out.write(result);
        out.close();

        imagePromos.add(createdFile);

        user.setImagePromos(imagePromos);

        imagePromoRepos.save(createdFile);
        userRepository.save(user);

        imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);

    }

    @Transactional(rollbackFor = {IOException.class})
    public List<ImagePromo> defaultUpload(Long userId) throws IOException {
        String key1 = UUID.randomUUID().toString();
        String key2 = UUID.randomUUID().toString();
        String key3 = UUID.randomUUID().toString();
        FileInputStream img1 = new FileInputStream("src/main/resources/imagepromo/promo_1.jpg");
        FileInputStream img2 = new FileInputStream("src/main/resources/imagepromo/promo_2.jpg");
        FileInputStream img3 = new FileInputStream("src/main/resources/imagepromo/promo_3.jpg");
        ImagePromo createdFile = new ImagePromo("promo_1",122L, key1, LocalDateTime.now());
        ImagePromo createdFile2 = new ImagePromo("promo_2",122L, key2, LocalDateTime.now());
        ImagePromo createdFile3= new ImagePromo("promo_3",122L, key3, LocalDateTime.now());


        List<ImagePromo> userListImgPromo = new ArrayList<>();
        userListImgPromo.add(createdFile);
        userListImgPromo.add(createdFile2);
        userListImgPromo.add(createdFile3);

        try {
            Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "promo_1");
            File directory = new File(path.getParent().toString());

            if (!directory.exists()) {
                directory.mkdir();
            }
            FileOutputStream fileOut = new FileOutputStream(path.toString());
                while (img1.available() > 0) {
                    int oneByte = img1.read();
                    fileOut.write(oneByte);
                }
                 img1.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        try {
            Path path2 = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "promo_2");
            File directory2 = new File(path2.getParent().toString());

            if (!directory2.exists()) {
                directory2.mkdir();
            }
            FileOutputStream fileOut2 = new FileOutputStream(path2.toString());
            while (img2.available() > 0) {
                int oneByte = img2.read();
                fileOut2.write(oneByte);
            }
            img2.close();
            fileOut2.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
                Path path3 = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "promo_3");
                File directory3 = new File(path3.getParent().toString());

                if (!directory3.exists()) {
                    directory3.mkdir();
                }
            FileOutputStream fileOut3 = new FileOutputStream(path3.toString());
            while (img3.available() > 0) {
                int oneByte = img3.read();
                fileOut3.write(oneByte);
            }
            img3.close();
            fileOut3.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        imagePromoRepos.saveAll(userListImgPromo);
        return userListImgPromo;

    }



    public ResponseEntity<Object> download(Long id, String key) throws IOException {
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
    public ImagePromo findByKey(String key) {
        return imagePromoRepos.findByKey(key).get();
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(Long id, String key) throws IOException {
        User user = userRepository.findById(id).get();
        List<ImagePromo> imagePromos = user.getImagePromos();

        ImagePromo file = imagePromoRepos.findByKey(key).get();

        imagePromos.remove(file);
        user.setImagePromos(imagePromos);
        userRepository.save(user);
        imagePromoRepos.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
            Files.delete(path);
        }catch (FileNotFoundException e) {
            System.out.println();
        }

        imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(element->{
                imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll(Long id) throws IOException {

        User user = userRepository.findById(id).get();
        List<ImagePromo> imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(element->{
            if(element.getName().equals("promo_1") || element.getName().equals("promo_2") || element.getName().equals("promo_3")){
                imageKeys.add(element.getName());
            }
            else{
                imageKeys.add(element.getKey());
            }
        });

        return ResponseEntity.ok().body(imageKeys);
    }
}