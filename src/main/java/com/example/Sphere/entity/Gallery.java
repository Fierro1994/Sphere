package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
public class Gallery extends FileEntity{
    public Gallery(String name, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name, size, key, keySmall, uploadDate);
    }
}