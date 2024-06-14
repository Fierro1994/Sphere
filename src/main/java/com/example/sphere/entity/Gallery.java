package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
public class Gallery extends FileEntity{
    public Gallery(String name, String format, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name,format, size, key, keySmall, uploadDate);
    }
}