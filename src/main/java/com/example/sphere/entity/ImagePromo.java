package com.example.sphere.entity;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
public class ImagePromo extends FileEntity{
    public ImagePromo(String name, String format, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name, format, size, key, keySmall, uploadDate);
    }
}