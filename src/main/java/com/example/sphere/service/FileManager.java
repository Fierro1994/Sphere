package com.example.sphere.service;

import com.example.sphere.repository.AvatarRepos;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
@RequiredArgsConstructor
@Service
public class FileManager{
    @Autowired
    AvatarRepos avatarRepos;
    @Autowired
    UserRepository userRepository;


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



    public ResponseEntity<Resource> download(String id, String category, String key, String format) throws IOException {
        Path path = null;

        if (format.equals("image/jpeg")){
            path = Paths.get("src/main/resources/storage/"+ id + "/" +category +"/" + key + "." + "jpg");
        }
        InputStream inputStream = new FileInputStream(path.toFile());
        Resource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .headers(getHeaders(path, format))
                .body(resource);
    }

    private HttpHeaders getHeaders(Path path, String format) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        if (format.equals("image/jpeg")){
            headers.setContentType(MediaType.IMAGE_JPEG);
        }

        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        return headers;
    }
}