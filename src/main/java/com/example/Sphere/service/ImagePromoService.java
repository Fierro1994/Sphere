package com.example.Sphere.service;

import com.example.Sphere.entity.FileEntity;
import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.ImagePromoRepos;
import com.example.Sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ImagePromoService {
    @Autowired
    ImagePromoRepos imagePromoRepos;
    @Autowired
    FileManager fileManager;
    @Autowired
    UserRepository userRepository;
    String nameFolder = "imagepromo";
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        ImagePromo createdFile = new ImagePromo(file.getOriginalFilename(), file.getSize(), key, keySmall, LocalDateTime.now());//        T entity = null;

        List<ImagePromo> imagePromos = user.getImagePromos();

        List<ImagePromo> defpromoList = user.getImagePromos();
        for (ImagePromo imagePromo : defpromoList) {
            if (imagePromo.getName().equals("promo_1") ){
                Path path = Paths.get("src/main/resources/storage/"+ user.getUserId() + "/" + nameFolder +"/" + imagePromo.getName());
                imagePromos.remove(imagePromo);
                imagePromoRepos.delete(imagePromo);
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.out.println("file "  + imagePromo.getName() + " not found");
                }
            }
        }
        imagePromos.add(createdFile);

        user.setImagePromos(imagePromos);

        imagePromoRepos.save(createdFile);
        userRepository.save(user);

        Path pathS = Paths.get("src/main/resources/storage/"+ userDetails.getUserId() + "/" + nameFolder);
        File directoryS = pathS.toFile();
        if (!directoryS.exists()) {
            directoryS.mkdirs();
        }
        Path imagePathS = Paths.get(pathS.toString(), keySmall + ".jpg");
        try (InputStream inputStreams = file.getInputStream();
            OutputStream outputStreams = new FileOutputStream(imagePathS.toFile())) {
            BufferedImage images = ImageIO.read(inputStreams);
            BufferedImage outputImage = Scalr.resize(images, 600);

            ImageWriter writerS = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam paramS = writerS.getDefaultWriteParam();
            if (paramS.canWriteCompressed()) {
                paramS.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                paramS.setCompressionQuality(0.5f);
            }

            writerS.setOutput(ImageIO.createImageOutputStream(outputStreams));
            writerS.write(null, new IIOImage(outputImage, null, null), paramS);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Path imagePathB = Paths.get(pathS.toString(), key + ".jpg");
        try (InputStream inputStreamB = file.getInputStream();
             OutputStream outputStreamB = new FileOutputStream(imagePathB.toFile())) {
            BufferedImage imageB = ImageIO.read(inputStreamB);
            BufferedImage outputImageB = Scalr.resize(imageB, 600);

            ImageWriter writerB = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam paramB = writerB.getDefaultWriteParam();
            if (paramB.canWriteCompressed()) {
                paramB.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                paramB.setCompressionQuality(0.5f);
            }

            writerB.setOutput(ImageIO.createImageOutputStream(outputStreamB));
            writerB.write(null, new IIOImage(outputImageB, null, null), paramB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> imageKeys = new ArrayList<>();
        List<ImagePromo> imagePromoList = userDetails.getImagePromos();
        imagePromoList.forEach(element->{
                String path = "http://localhost:3000/imagepromo/" + user.getUserId() + "/" + element.getKey() + ".jpg";
                imageKeys.add(path);
        });
        System.out.println(imageKeys.size());

        return ResponseEntity.ok().body(imageKeys);

    }

    @Transactional(rollbackFor = {IOException.class})
    public List<ImagePromo> defaultUpload(String userId) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        FileInputStream img1 = new FileInputStream("src/main/resources/imagepromo/promo_1.jpg");
        ImagePromo createdFile = new ImagePromo("promo_1",122L, key,keySmall, LocalDateTime.now());

        List<ImagePromo> userListImgPromo = new ArrayList<>();
        userListImgPromo.add(createdFile);

        try {
            Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + "promo_1.jpg");
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

        imagePromoRepos.saveAll(userListImgPromo);
        return userListImgPromo;

    }



    public ResponseEntity<?> download(String id, String key) throws IOException {
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" +nameFolder +"/" + key);
        return fileManager.download(id, key, nameFolder);
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
            Path path = Paths.get("src/main/resources/storage/"+ user.getUserId() + "/" + nameFolder +"/" + key);
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

    public ResponseEntity<?> showAll() {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        if (userDetails != null){
            List<ImagePromo> imagePromos = userDetails.getImagePromos();

            List<String> imageKeys = new ArrayList<>();
            for (ImagePromo imagePromo : imagePromos){
                if(imagePromo.getName().equals("promo_1")){
                    String path = "http://localhost:3000/imagepromo/" + userDetails.getUserId() + "/" + imagePromo.getName() + ".jpg";
                    imageKeys.add(path);
                }
                else{
                    String path = "http://localhost:3000/imagepromo/" + userDetails.getUserId() + "/" + imagePromo.getKey() + ".jpg";
                    imageKeys.add(path);
                }
            }

            return ResponseEntity.ok().body(imageKeys);

        }
        else return ResponseEntity.notFound().build();
    }
}