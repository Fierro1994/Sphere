package com.example.Sphere.service;

import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.ImagePromoRepos;
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
    public ResponseEntity<?> upload(String file, String name, int size, String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();

        ImagePromo createdFile = new ImagePromo(name, size, key, keySmall,LocalDateTime.now());
        User user = userRepository.findByuserId(userId).get();
        List<ImagePromo> imagePromos = user.getImagePromos();

        List<ImagePromo> defpromoList = user.getImagePromos();
        for (ImagePromo imagePromo : defpromoList) {
            if (imagePromo.getName().equals("promo_1") ){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + imagePromo.getName());
                imagePromos.remove(imagePromo);
                imagePromoRepos.delete(imagePromo);
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + imagePromo.getName() + " not found");
                }
            }
            if (imagePromo.getName().equals("promo_2") ){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + imagePromo.getName());
                imagePromos.remove(imagePromo);
                imagePromoRepos.delete(imagePromo);
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + imagePromo.getName() + " not found");
                }
            }
            if (imagePromo.getName().equals("promo_3") ){
                Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + imagePromo.getName());
                imagePromos.remove(imagePromo);
                imagePromoRepos.delete(imagePromo);
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + imagePromo.getName() + " not found");
                }
            }
        }

      fileManager.upload(file, userId, nameFolder, key, keySmall);

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
    public List<ImagePromo> defaultUpload(String userId) throws IOException {
        String key1 = UUID.randomUUID().toString();
        String key2 = UUID.randomUUID().toString();
        String key3 = UUID.randomUUID().toString();
        FileInputStream img1 = new FileInputStream("src/main/resources/imagepromo/promo_1.jpg");
        FileInputStream img2 = new FileInputStream("src/main/resources/imagepromo/promo_2.jpg");
        FileInputStream img3 = new FileInputStream("src/main/resources/imagepromo/promo_3.jpg");
        ImagePromo createdFile = new ImagePromo("promo_1",122, key1, LocalDateTime.now());
        ImagePromo createdFile2 = new ImagePromo("promo_2",122, key2, LocalDateTime.now());
        ImagePromo createdFile3= new ImagePromo("promo_3",122, key3, LocalDateTime.now());


        List<ImagePromo> userListImgPromo = new ArrayList<>();
        userListImgPromo.add(createdFile);
        userListImgPromo.add(createdFile2);
        userListImgPromo.add(createdFile3);

        try {
            Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "promo_1");
            File directory = new File(path.getParent().toString());

            if (!directory.exists()) {
                directory.mkdirs();
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
                directory2.mkdirs();
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
                    directory3.mkdirs();
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



    public ResponseEntity<Object> download(String id, String key) throws IOException {
        return fileManager.download(id,key,nameFolder);
    }

    @Transactional(readOnly = true)
    public ImagePromo findByKey(String key) {
        return imagePromoRepos.findByKey(key).get();
    }

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String id, String key) throws IOException {
        User user = userRepository.findByuserId(id).get();
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

    public ResponseEntity<?> showAll(String id) throws IOException {
        User user = userRepository.findByuserId(id).get();
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