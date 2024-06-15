package com.example.sphere.service;

import com.example.sphere.entity.*;
import com.example.sphere.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileManager {
    private final ExecutorService operationExecutor = Executors.newFixedThreadPool(4);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AvatarRepos avatarRepos;
    @Autowired
    private MomentsRepository momentsRepository;
    @Autowired
    private ImagePromoRepos imagePromoRepos;

    @Transactional(rollbackFor = {IOException.class})
    public void upload(MultipartFile file, String category, String startTrim, String endTrim) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        operationExecutor.submit(() -> saveToDatabase(file, category, key, keySmall, user));
        if (file.getContentType().startsWith("image")) {
            try {
                saveImageInStorage(file, key, keySmall, userDetails.getUserId(), category);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        } else if (file.getContentType().startsWith("video")) {
                try {
                    saveVideoInStorage(file, key, keySmall, userDetails.getUserId(), category, startTrim, endTrim);
                } catch (IOException | EncoderException e) {
                    throw new RuntimeException(e);
                }
        }

    }

    public ResponseEntity<Resource> download(HttpHeaders headers, String id, String category, String key, String format) throws IOException {

        if (format.equals("image/jpeg")) {
            Path path = null;
            path = Paths.get("src/main/resources/storage/" + id + "/" + category + "/" + key + "." + "jpg");
            InputStream inputStream = new FileInputStream(path.toFile());
            Resource resource = new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .headers(getHeaders(path, format))
                    .body(resource);
        }
        if (format.equals("video/mp4")) {
            Path path = null;
            try {
                path = Paths.get("src/main/resources/storage/" + id + "/" + category + "/" + key + "." + "mp4");
                Resource resource = new UrlResource(path.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                    return ResponseEntity.notFound().build();
                }

                long fileLength = resource.contentLength();
                HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
                long rangeStart = 0;
                long rangeEnd = fileLength - 1;

                if (range != null) {
                    rangeStart = range.getRangeStart(fileLength);
                    rangeEnd = range.getRangeEnd(fileLength);
                }
                InputStream inputStream = resource.getInputStream();
                inputStream.skip(rangeStart);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                StreamUtils.copy(inputStream, buffer);

                byte[] byteArray = buffer.toByteArray();
                return ResponseEntity.status(range != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(byteArray.length))
                        .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength)
                        .body(new ByteArrayResource(byteArray));
            } catch (MalformedInputException e) {
                return ResponseEntity.badRequest().build();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.ok().build();
    }

    private HttpHeaders getHeaders(Path path, String format) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        if (format.equals("image/jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }

        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        return headers;
    }


    private void saveImageInStorage(MultipartFile file, String key, String keySmall, String userId, String category) throws IOException {
        Path path = Paths.get("src/main/resources/storage/" + userId + "/" + category);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        saveResizedImage(file, Paths.get(path.toString(), keySmall + ".jpg"), 600, 0.5f);
        saveResizedImage(file, Paths.get(path.toString(), key + ".jpg"), 800, 0.8f);
    }


    private void saveResizedImage(MultipartFile file, Path imagePath, int width, float quality) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage outputImage = Scalr.resize(image, width);

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);
            }

            writer.setOutput(ImageIO.createImageOutputStream(outputStream));
            writer.write(null, new IIOImage(outputImage, null, null), param);
            log.info("Изображение сохранено в: " + imagePath.toString());

        }
    }


    private void saveVideoInStorage(MultipartFile file, String key, String keySmall, String userId, String category, String startTrim, String endTrim) throws IOException, EncoderException {
        Path path = Paths.get("src/main/resources/storage/" + userId + "/" + category + "/" + file.getName());
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/" + userId + "/" + category + "/" + file.getName());
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
        File target = new File("src/main/resources/storage/" + userId + "/" + category + "/" + key + ".mp4");
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
    }

    private void saveToDatabase(MultipartFile file, String category, String key, String keySmall, User user) {
        switch (category) {
            case "avatars":
                Avatar createdAvatar = new Avatar(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());
                user.getAvatar().add(createdAvatar);
                avatarRepos.save(createdAvatar);
                break;
            case "moments":
                Moments createdMoments = new Moments(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());
                user.getMomentsList().add(createdMoments);
                momentsRepository.save(createdMoments);
                break;
            case "imagepromo":
                ImagePromo createdPromo = new ImagePromo(file.getOriginalFilename(), file.getContentType(), file.getSize(), key, keySmall, LocalDateTime.now());
                user.getImagePromos().add(createdPromo);
                imagePromoRepos.save(createdPromo);
                break;
            default:
                break;
        }
        userRepository.save(user);
    }
}