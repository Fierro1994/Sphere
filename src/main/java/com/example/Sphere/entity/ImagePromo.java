package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ImagePromo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long size;
    @Column(name = "key_name")
    private String key;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public ImagePromo(String name, Long size, String key, LocalDateTime uploadDate) {
        this.name = name;
        this.size = size;
        this.key = key;
        this.uploadDate = uploadDate;
    }


}