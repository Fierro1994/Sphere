package com.example.Sphere.controllers;

import com.example.Sphere.models.request.AddMomentsRequest;
import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.service.MomentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/moments")
@RequiredArgsConstructor
public class MomentsController {
    private final MomentsService momentsService;

    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, String userId, String startTrim, String endTrim) throws IOException, EncoderException {
        return new ResponseEntity<>(momentsService.upload( file, userId, startTrim, endTrim), HttpStatus.CREATED);

    }
    @PostMapping(path = "/upload_img")
    public ResponseEntity<?> uploadImg(@RequestParam("file") String file, String name, Long size, String userId, String type) throws IOException {
        return new ResponseEntity<>(momentsService.uploadimg( file, name, size, userId, type),HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}/{key}.{format}")
    public ResponseEntity<Object> download(@PathVariable("id") String id, @PathVariable("key") String key,  @PathVariable("format") String format) {
        try {
            return momentsService.download(id, key, format);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}/{key}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return  ResponseEntity.ok(momentsService.delete(id, key));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(path = "/listmoments")
    public ResponseEntity<?> getAll(@RequestBody GetAllIReq req ) throws IOException {
        return  ResponseEntity.ok(momentsService.getAll(req.getUserId()));
    }


}
