package com.example.Sphere.entity;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
public class ImagePromo extends FileEntity{
    public ImagePromo(String name, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name, size, key, keySmall, uploadDate);
    }
}