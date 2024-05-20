package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ImagePromo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int countLikes;
    private int countReposts;
    private boolean isFavorites;
    private String format;
    private int size;
    @Column(name = "key_name")
    private String key;
    @Column(name = "key_small")
    private String keySmall;
    @Column(name = "is_arhiv")
    private boolean isArhiv;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public ImagePromo(String name, int size, String key,LocalDateTime uploadDate) {
        this.name = name;
        this.size = size;
        this.key = key;
        this.uploadDate = uploadDate;
    }
    public ImagePromo(String name, int size, String key, String keySmall,LocalDateTime uploadDate) {
        this.name = name;
        this.size = size;
        this.key = key;
        this.keySmall = keySmall;
        this.uploadDate = uploadDate;
    }



}