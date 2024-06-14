package com.example.sphere.service;

import com.example.sphere.repository.AvatarRepos;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@RequiredArgsConstructor
@Service
public class FileManager{
    @Autowired
    private AvatarRepos avatarRepos;
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<Resource> download(HttpHeaders headers, String id, String category, String key, String format) throws IOException {

        if (format.equals("image/jpeg")){
            Path path = null;
            path = Paths.get("src/main/resources/storage/"+ id + "/" +category +"/" + key + "." + "jpg");
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

        if (format.equals("image/jpeg")){
            headers.setContentType(MediaType.IMAGE_JPEG);
        }

        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        return headers;
    }
}