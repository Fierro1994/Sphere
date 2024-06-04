package com.example.sphere.controllers;


import com.example.sphere.models.request.GetAllIReq;
import com.example.sphere.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/avatar")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")
public class AvatarControllers {
    @Autowired
    AvatarService avatarService;
    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") String file, String name, Long size, String userId) throws IOException {
        return new ResponseEntity<>(avatarService.upload( file, userId),HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<?> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {
            return avatarService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/listavatars")
    public ResponseEntity<?> showAll(@RequestBody GetAllIReq req ) throws IOException {
        return  ResponseEntity.ok(avatarService.showAll(req.getUserId()));
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