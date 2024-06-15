package com.example.sphere.controllers.filesControllers;

import com.example.sphere.service.FileManager;
import com.example.sphere.service.ImagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "/imagepromo")
@RequiredArgsConstructor
public class ImagePromoController {
    @Autowired
    ImagePromoService imagePromoService;
    @Autowired
    FileManager fileManager;

    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ResponseEntity<>(imagePromoService.upload(file),HttpStatus.CREATED);
    }

    @GetMapping(path = "/listpromo")
    public ResponseEntity<?> showAll( ) {
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