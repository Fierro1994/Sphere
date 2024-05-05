package com.example.Sphere.service;

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
    public Gallery upload(MultipartFile file, Long userId) throws IOException {
        String key = UUID.randomUUID().toString();
        Gallery createdFile = new Gallery(file.getOriginalFilename(), file.getSize(), key, LocalDateTime.now());
        User user = userRepository.findById(userId).get();

        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdir();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        convertFile.createNewFile();
        OutputStream out = new FileOutputStream(convertFile);
        BufferedImage image = ImageIO.read(file.getInputStream());
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if(param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
        }

        writer.write(null, new IIOImage(image, null, null), param);
        System.out.println("size posle" + image.getData());
        out.close();
        ios.close();
        writer.dispose();

        List<Gallery> userListGallery = user.getGalleries();
        userListGallery.add(createdFile);
        user.setGalleries(userListGallery);
        galleryRepos.saveAll(userListGallery);
        userRepository.save(user);
        return createdFile;
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
    public Gallery findByKey(String key) {
        return galleryRepos.findByKey(key).get();
    }

    @Transactional(rollbackFor = {IOException.class})
    public void delete(Long id, String key) throws IOException {
        Gallery file = galleryRepos.findByKey(key).get();
        galleryRepos.delete(file);
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        Files.delete(path);
        fileManager.delete(id, key,nameFolder);
    }

    public ResponseEntity<?> showAll(Long id) throws IOException {
        User user = userRepository.findById(id).get();
        List<Gallery> galleryList = user.getGalleries();
        List<String> imageKeys = new ArrayList<>();
        galleryList.forEach(el -> {
            imageKeys.add(el.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }
}