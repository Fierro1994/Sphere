package com.example.sphere.controllers.filesControllers;


import com.example.sphere.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "/avatar")
@RequiredArgsConstructor
public class AvatarControllers {
    @Autowired
    AvatarService avatarService;
    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestParam("avatar") MultipartFile file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ResponseEntity<>(avatarService.upload(file),HttpStatus.CREATED);
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