package com.example.Sphere.controllers;
import com.example.Sphere.models.request.GetAllIReq;
import com.example.Sphere.models.request.InfoUploadReq;
import com.example.Sphere.service.InfoModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/infomodule")
@RequiredArgsConstructor
@CrossOrigin(value ="http://localhost:3000/", allowCredentials = "true")

public class InfoModuleController {
    @Autowired
    InfoModuleService infoModuleService;

    @PostMapping(path = "/listmodules")
    public ResponseEntity<?> getAll(@RequestBody GetAllIReq req ) throws IOException {
        return  ResponseEntity.ok(infoModuleService.getAll(req.getUserId()));
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(@RequestBody InfoUploadReq req) throws IOException {

        return  ResponseEntity.ok(infoModuleService.upload(req.getUserId(), req.getInfoModules()));
    }


}