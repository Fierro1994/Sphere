package com.example.Sphere.service;

import com.example.Sphere.repository.AvatarRepos;
import com.example.Sphere.repository.UserRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileManager{
    @Autowired
    private AvatarRepos avatarRepos;
    @Autowired
    private UserRepository userRepository;


    @Transactional(rollbackFor = {IOException.class})
    public void upload(String file,String userId, String nameFolder, String key, String keySmall) throws IOException {



        String base64ImageString = file.replace("data:image/jpeg;base64,", "");
        byte[] result = Base64.getDecoder().decode(base64ImageString);

        Path pathS = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + keySmall);
        File directoryS = new File(pathS.getParent().toString());
        if (!directoryS.exists()) {
            directoryS.mkdirs();
        }
        File convertFileS = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + keySmall);
        convertFileS.createNewFile();
        InputStream targetStreamS = new ByteArrayInputStream(result);
        OutputStream outS = new FileOutputStream(convertFileS);

        BufferedImage imageS = ImageIO.read(targetStreamS);
        BufferedImage outputImageS = Scalr.resize(imageS, 300);


        ImageWriter writerS = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream iosS = ImageIO.createImageOutputStream(outS);
        writerS.setOutput(iosS);

        ImageWriteParam paramS = writerS.getDefaultWriteParam();
        if(paramS.canWriteCompressed()){
            paramS.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            paramS.setCompressionQuality(0.5f);
        }

        writerS.write(null, new IIOImage(outputImageS, null, null), paramS);
        outS.close();
        iosS.close();
        writerS.dispose();
////////

        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key);
        convertFile.createNewFile();

        InputStream targetStream = new ByteArrayInputStream(result);
        OutputStream out = new FileOutputStream(convertFile);


        BufferedImage image = ImageIO.read(targetStream);
        BufferedImage outputImage = Scalr.resize(image, 600);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if(param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(1f);
        }

        writer.write(null, new IIOImage(outputImage, null, null), param);
        out.close();
        ios.close();
        writer.dispose();
    }



    public ResponseEntity<Object> download(String id, String key, String nameFolder) throws IOException {
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


}