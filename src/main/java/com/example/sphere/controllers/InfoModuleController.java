package com.example.sphere.controllers;
import com.example.sphere.models.request.GetAllIReq;
import com.example.sphere.models.request.InfoUploadReq;
import com.example.sphere.service.InfoModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/infomodule")
@RequiredArgsConstructor

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