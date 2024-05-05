package com.example.Sphere.service;


import com.example.Sphere.entity.ImagePromo;
import com.example.Sphere.entity.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class FileManager {
    public void upload(MultipartFile file, String key, Long id, String nameFolder) throws IOException {
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        System.out.println("size do" + file.getSize());
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdir();
        }
        File convertFile = new File("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        convertFile.createNewFile();

        BufferedImage image = ImageIO.read(convertFile);

        File outputFile = new File("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        OutputStream out = new FileOutputStream(outputFile);

        ImageWriter writer = ImageIO.getImageWritersByFormatName( file.getContentType()).next();
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
    }

    public ResponseEntity<Object> download(Long id, String key, String nameFolder) throws IOException {
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

    public ResponseEntity<?> downloadAll(User user) throws IOException {
        List<ImagePromo> imagePromos = user.getImagePromos();
        List<String> imageKeys = new ArrayList<>();
        imagePromos.forEach(el -> {
            imageKeys.add(el.getKey());
        });
        return ResponseEntity.ok().body(imageKeys);
    }

    public void delete(Long id, String key, String nameFolder) throws IOException {
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
        Files.delete(path);
    }
}
