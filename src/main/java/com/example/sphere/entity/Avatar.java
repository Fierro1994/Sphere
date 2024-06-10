package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Avatar extends FileEntity{
    public Avatar(String name, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        super(name, size, key, keySmall, uploadDate);
    }
}