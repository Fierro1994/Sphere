package com.example.Sphere.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward(){
        return "forward:/";
    }
}
