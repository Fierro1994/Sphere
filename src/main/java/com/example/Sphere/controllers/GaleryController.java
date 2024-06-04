package com.example.Sphere.controllers;

import com.example.Sphere.entity.Gallery;
import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping(value = "/gallery")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

public class GaleryController {
    @Autowired
    GalleryService galleryService;

    @PostMapping
    public ResponseEntity<Gallery> upload(@RequestParam("file") String file, String userId) throws IOException {

        return new ResponseEntity<>(galleryService.upload(file, userId), HttpStatus.CREATED);

    }

    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<?> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {
            return galleryService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/listgallery")
    public ResponseEntity<?> showAll(@RequestBody GetAllIReq req) throws IOException {
        return ResponseEntity.ok(galleryService.showAll(req.getUserId()));
    }

    @DeleteMapping(value = "/{id}/{key}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {
            galleryService.delete(id, key);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}