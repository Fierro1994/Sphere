package com.example.Sphere.controllers;

import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.service.ImagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/imagepromo")
@RequiredArgsConstructor
public class ImagePromoController {
    @Autowired
    ImagePromoService imagePromoService;
    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") String file, String name, Long size, String userId) throws IOException {
        return new ResponseEntity<>(imagePromoService.upload( file, name, size, userId),HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<Object> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {
            return imagePromoService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/listpromo")
    public ResponseEntity<?> showAll(@RequestBody GetAllIReq req ) throws IOException {
        return  ResponseEntity.ok(imagePromoService.showAll(req.getUserId()));
    }

    @DeleteMapping(value = "/{id}/{key}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return  ResponseEntity.ok(imagePromoService.delete(id, key));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}