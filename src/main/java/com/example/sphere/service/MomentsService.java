package com.example.sphere.service;

import com.example.sphere.entity.*;
import com.example.sphere.repository.MomentsRepository;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MomentsService {

    private final UserRepository userRepository;
    @Autowired
    private FileManager fileManager;
    private final MomentsRepository momentsRepository;
    private final String nameFolder = "moments";

    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> upload(MultipartFile file, String userId, String startTrim, String endTrim) throws IOException, EncoderException {
        String key = UUID.randomUUID().toString();
        String keySmall = UUID.randomUUID().toString();


        String format = "mp4";
        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + file.getName() );
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + file.getName());
        convertFile.createNewFile();
        try(InputStream fin= file.getInputStream() ;
            FileOutputStream fos= new FileOutputStream(convertFile)
        )
        {
            byte[] buffer = new byte[256];
            int count;
            while((count=fin.read(buffer))!=-1){

                fos.write(buffer, 0, count);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
if (endTrim == null){
    endTrim = "10";
}
        float Offset = Float.parseFloat(startTrim);
        float duration = Float.parseFloat(endTrim) - Float.parseFloat(startTrim);
        File source = new File( convertFile.getPath());
        File target = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key + ".mp4" );
        AudioAttributes audio = new AudioAttributes();
        VideoAttributes video = new VideoAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        audio.setBitRate(64000);
        audio.setSamplingRate(44100);
        audio.setChannels(2);
        audio.setBitRate(192000);
        video.setBitRate(250000);
        video.setFrameRate(25);
        attrs.setOffset(Offset);
        attrs.setDuration(duration);
        attrs.setOutputFormat("mp4");
        Encoder instance = new Encoder();
        instance.encode(new MultimediaObject(source), target, attrs, null);
        Files.delete(path);

        Moments createdFile = new Moments(file.getOriginalFilename(), file.getSize(), format,  key, LocalDateTime.now());
        User user = userRepository.findByuserId(userId).get();
        List<Moments> userListMoments = user.getMomentsList();
        userListMoments.add(createdFile);
        user.setMomentsList(userListMoments);
        momentsRepository.save(createdFile);
        userRepository.save(user);
        List<Moments> momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element->{
            momentsKeys.add(element.getKey() + "." + element.getFormat());
        });
        return ResponseEntity.ok().body(momentsKeys);
    }


    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<?> uploadimg(String file, String name, Long size, String userId, String type) throws IOException {
        String key = UUID.randomUUID().toString();
        String format = type.substring( 6);
        Moments createdFile = new Moments(name, size,format, key,  LocalDateTime.now());
        User user = userRepository.findByuserId(userId).get();
        List<Moments> momentsList = user.getMomentsList();
        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key + "." + format);
        File directory = new File(path.getParent().toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertFile = new File("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + key +"." + format);
        convertFile.createNewFile();
        String base64ImageString = file.replace("data:image/jpeg;base64,", "");
        byte[] result = Base64.getDecoder().decode(base64ImageString);
        OutputStream out = new FileOutputStream(convertFile);
        out.write(result);
        out.close();

        momentsList.add(createdFile);

        user.setMomentsList(momentsList);
        momentsRepository.save(createdFile);
        userRepository.save(user);

        momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element->{
            momentsKeys.add(element.getKey() + "." + element.getFormat());

        });
        return ResponseEntity.ok().body(momentsKeys);

    }

    public ResponseEntity<Object> download(String id, String key, String format) throws IOException {
        Path path = Paths.get("src/main/resources/storage/"+ id + "/" +nameFolder +"/" + key + "." + format);
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
                file.length()).contentType(MediaType.parseMediaType("video/mp4")).body(resource);
        return responseEntity;
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
            Path path = Paths.get("src/main/resources/storage/"+ id + "/" + nameFolder +"/" + key);
            Files.delete(path);
        }catch (FileNotFoundException e) {
            System.out.println();
        }

        momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element->{
            momentsKeys.add(element.getKey() + "." + element.getFormat());
        });
        return ResponseEntity.ok().body(momentsKeys);
    }

    public ResponseEntity<?> getAll(String id) throws IOException {

        User user = userRepository.findByuserId(id).get();
        List<Moments> momentsList = user.getMomentsList();
        List<String> momentsKeys = new ArrayList<>();
        momentsList.forEach(element->{
            momentsKeys.add(element.getKey() + "." + element.getFormat());
        });

        return ResponseEntity.ok().body(momentsKeys);
    }
}
