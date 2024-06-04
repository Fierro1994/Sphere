package com.example.Sphere.controllers;

import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.service.ImagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/imagepromo")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")
public class ImagePromoController {
    @Autowired
    ImagePromoService imagePromoService;

    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(imagePromoService.upload(file),HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<?> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return imagePromoService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/listpromo")
    public ResponseEntity<?> showAll( )  {
        return  imagePromoService.showAll( );
    }

    @DeleteMapping(value = "/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key) {
        try {

            return  ResponseEntity.ok(imagePromoService.delete(key));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}