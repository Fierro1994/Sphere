package com.example.Sphere.models.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MessageMail {
    private String name;
    private String numberPhone;
    private String message;

}
