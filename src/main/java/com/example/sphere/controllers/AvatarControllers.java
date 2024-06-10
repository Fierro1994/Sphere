package com.example.sphere.controllers;


import com.example.sphere.models.request.GetAllIReq;
import com.example.sphere.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/avatar")
@RequiredArgsConstructor
public class AvatarControllers {
    @Autowired
    AvatarService avatarService;
    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("avatar") MultipartFile file) throws IOException {
        avatarService.upload(file);
        return ResponseEntity.ok().build();

    }
    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<?> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return avatarService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/listavatars")
    public ResponseEntity<?> showAll( )  {
        return  avatarService.showAll( );
    }

    @DeleteMapping(value = "/{id}/{key}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return  ResponseEntity.ok(avatarService.delete(id, key));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}