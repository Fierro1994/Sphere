package com.example.sphere.service;

import com.example.sphere.entity.*;
import com.example.sphere.repository.MomentsRepository;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class MomentsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private MomentsRepository momentsRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UrlGenerator urlGenerator;
    private final String category = "moments";

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(MultipartFile file, String startTrim, String endTrim) throws IOException, EncoderException {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();

        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();


        String format = "mp4";
        Path path = Paths.get("src/main/resources/storage/" + userDetails.getUserId() + "/" + category + "/" + file.getName());
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/" + userDetails.getUserId() + "/" + category + "/" + file.getName());
        convertFile.createNewFile();
        try (InputStream fin = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(convertFile)
        ) {
            byte[] buffer = new byte[256];
            int count;
            while ((count = fin.read(buffer)) != -1) {

                fos.write(buffer, 0, count);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        if (endTrim == null) {
            endTrim = "10";
        }
        float Offset = Float.parseFloat(startTrim);
        float duration = Float.parseFloat(endTrim) - Float.parseFloat(startTrim);
        File source = new File(convertFile.getPath());
        File target = new File("src/main/resources/storage/" + userDetails.getUserId() + "/" + category + "/" + key + ".mp4");
        AudioAttributes audio = new AudioAttributes();
        VideoAttributes video = new VideoAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        audio.setBitRate(192000);
        audio.setSamplingRate(44100);
        audio.setChannels(2);
        video.setBitRate(1000000);
        video.setFrameRate(30);
        video.setSize(new VideoSize(1280, 720));
        attrs.setOffset(Offset);
        attrs.setDuration(duration);
        attrs.setOutputFormat("mp4");
        Encoder instance = new Encoder();
        instance.encode(new MultimediaObject(source), target, attrs, null);
        Files.delete(path);

        Moments createdFile = new Moments(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());
        User user = userRepository.findByuserId(userDetails.getUserId()).get();
        List<Moments> userListMoments = user.getMomentsList();
        userListMoments.add(createdFile);
        user.setMomentsList(userListMoments);
        momentsRepository.save(createdFile);
        userRepository.save(user);
        List<Moments> momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element -> {
            momentsKeys.add(element.getKey() + "." + element.getFormat());
        });
        return ResponseEntity.ok().body(momentsKeys);
    }


    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Moments createdFile = new Moments(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());
        List<Moments> momentsList = user.getMomentsList();

        momentsList.add(createdFile);

        user.setMomentsList(momentsList);

        momentsRepository.save(createdFile);
        userRepository.save(user);

        Path pathS = Paths.get("src/main/resources/storage/" + userDetails.getUserId() + "/" + category);
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
        List<Moments> moments = user.getMomentsList();
        moments.forEach(element -> {
            String path = urlGenerator.generateTemporaryUrl(userDetails.getUserId(), element.getKey(), element.getFormat(), category);
            imageKeys.add(path);
        });
        return ResponseEntity.ok().body(imageKeys);

    }


    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> delete(String id, String key) throws IOException {
        User user = userRepository.findByuserId(id).get();
        List<Moments> momentsList = user.getMomentsList();
        String[] subKey = key.split("\\.");

        Moments file = momentsRepository.findByKey(subKey[0]).get();

        momentsList.remove(file);
        user.setMomentsList(momentsList);
        userRepository.save(user);
        momentsRepository.delete(file);
        try {
            Path path = Paths.get("src/main/resources/storage/" + id + "/" + category + "/" + key);
            Files.delete(path);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element -> {
            momentsKeys.add(element.getKey() + "." + element.getFormat());
        });
        return ResponseEntity.ok().body(momentsKeys);
    }

    public ResponseEntity<?> showAll() {
        UserDetailsImpl userDetails = userDetailsService.loadUserFromContext();
        if (userDetails != null) {
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            List<Moments> momentsList = user.getMomentsList();


            List<String> imageKeys = new ArrayList<>();
            for (Moments moments : momentsList) {
                String path = urlGenerator.generateTemporaryUrl(userDetails.getUserId(), moments.getKey(), moments.getFormat(), category);

                imageKeys.add(path);
            }

            return ResponseEntity.ok().body(imageKeys);

        } else return ResponseEntity.notFound().build();
    }
}
