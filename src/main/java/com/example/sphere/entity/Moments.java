package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Moments extends FileEntity{
    public Moments(String name, String format, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name, format, size, key, keySmall, uploadDate);
    }
}
