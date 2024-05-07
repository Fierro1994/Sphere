package com.example.Sphere.service;

import com.example.Sphere.entity.Gallery;
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
public class ImagePromoService {
    @Autowired
    private ImagePromoRepos imagePromoRepos;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private UserRepository userRepository;
    private String nameFolder = "imagepromo";

    @Transactional(rollbackFor = {IOException.class})
    public ImagePromo upload(MultipartFile file, Long userId) throws IOException {
        String key = UUID.randomUUID().toString();
        ImagePromo createdFile = new ImagePromo(file.getOriginalFilename(), file.getSize(), key, LocalDateTime.now());
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

        List<ImagePromo> userListImgPromo = user.getImagePromos();
        userListImgPromo.add(createdFile);
        user.setImagePromos(userListImgPromo);
        imagePromoRepos.saveAll(userListImgPromo);
        userRepository.save(user);
        return createdFile;
    }

    @Transactional(rollbackFor = {IOException.class})
    public List<ImagePromo> defaultUpload() throws IOException {
        String key = UUID.randomUUID().toString();
        File file = new File("src/main/resources/imagepromo/promo_1.jpg");
        File file2 = new File("src/main/resources/imagepromo/promo_2.jpg");
        File file3 = new File("src/main/resources/imagepromo/promo_1.jpg");

        ImagePromo createdFile = new ImagePromo("promo_1",file.lastModified(), key, LocalDateTime.now());
        ImagePromo createdFile2 = new ImagePromo("promo_2",file.lastModified(), key, LocalDateTime.now());
        ImagePromo createdFile3= new ImagePromo("promo_3",file.lastModified(), key, LocalDateTime.now());


        List<ImagePromo> userListImgPromo = new ArrayList<>();
        userListImgPromo.add(createdFile);
        userListImgPromo.add(createdFile2);
        userListImgPromo.add(createdFile3);
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
    public void delete(Long id, String key) throws IOException {
        ImagePromo file = imagePromoRepos.findByKey(key).get();
        imagePromoRepos.delete(file);
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        Files.delete(path);
        fileManager.delete(id, key,nameFolder);
    }

    public ResponseEntity<?> showAll(Long id) throws IOException {

        User user = userRepository.findById(id).get();
        List<ImagePromo> imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(el -> {
            imageKeys.add(el.getName());
        });
        return ResponseEntity.ok().body(imageKeys);
    }
}