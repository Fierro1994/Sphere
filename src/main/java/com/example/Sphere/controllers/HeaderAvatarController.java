package com.example.Sphere.controllers;

import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.service.HeaderAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/header")
@RequiredArgsConstructor
public class HeaderAvatarController {
    @Autowired
    HeaderAvatarService headerAvatarService;
    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") String file, String name, Long size, String userId) throws IOException {
        return new ResponseEntity<>(headerAvatarService.upload( file, name, size, userId),HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}/{key}")
    public ResponseEntity<Object> download(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {
            return headerAvatarService.download(id, key);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/listheader")
    public ResponseEntity<?> showAll(@RequestBody GetAllIReq req ) throws IOException {
        return  ResponseEntity.ok(headerAvatarService.showAll(req.getUserId()));
    }

    @DeleteMapping(value = "/{id}/{key}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, @PathVariable("key") String key) {
        try {

            return  ResponseEntity.ok(headerAvatarService.delete(id, key));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}