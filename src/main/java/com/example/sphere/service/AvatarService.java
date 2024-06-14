package com.example.sphere.service;

import com.example.sphere.entity.Avatar;
import com.example.sphere.entity.ImagePromo;
import com.example.sphere.entity.User;
import com.example.sphere.repository.AvatarRepos;
import com.example.sphere.repository.UserRepository;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Slf4j
@Service
public class AvatarService {
    @Autowired
    AvatarRepos avatarRepos;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileManager fileManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UrlGenerator urlGenerator;
    private final String category = "avatars";


    @Transactional(rollbackFor = {IOException.class})
    public void upload(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Avatar createdFile = new Avatar(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());

        avatarRepos.save(createdFile);


        user.getAvatar().add(createdFile);
        userRepository.save(user);

        Path pathS = Paths.get("src/main/resources/storage/"+ userDetails.getUserId() + "/" + category);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
        }
        List<String> imageKeys = new ArrayList<>();
        List<Avatar> avatarList = userDetails.getAvatars();
        avatarList.forEach(element->{
            String path = "http://localhost:3000/avatar/" + user.getUserId() + "/" + element.getKey() + ".jpg";
            imageKeys.add(path);
        });

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
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + category +"/" + key);
            Files.delete(path);
            Path pathS = Paths.get("src/main/resources/storage/"+ id + "/" + category +"/" + file.getKeySmall());
            Files.delete(pathS);
        }catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        avatars = user.getAvatar();
        List<String> imageKeys = new ArrayList<>();
        avatars.forEach(element->{
            imageKeys.add(element.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public ResponseEntity<?> showAll() {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        if (userDetails != null){
            List<Avatar> avatars = userDetails.getAvatars();
            List<String> imageKeys = new ArrayList<>();
            for (Avatar avatar : avatars){
                String path = urlGenerator.generateTemporaryUrl(userDetails.getUserId(), avatar.getKey(),avatar.getFormat(), category);
                imageKeys.add(path);
            }
            return ResponseEntity.ok().body(imageKeys);
        }
        else return ResponseEntity.notFound().build();
    }



    @Transactional(rollbackFor = {IOException.class})
    public void regUpload(String email,MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Avatar createdFile = new Avatar(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());

        avatarRepos.save(createdFile);


        user.getAvatar().add(createdFile);
        userRepository.save(user);

        Path pathS = Paths.get("src/main/resources/storage/"+ user.getUserId() + "/" + category);
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

    }
}