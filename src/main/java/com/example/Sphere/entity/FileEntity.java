package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@MappedSuperclass
@Data
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int countLikes;
    private int countReposts;
    private boolean isFavorites;
    private String format;
    private Long size;
    @Column(name = "key_name")
    private String key;
    @Column(name = "key_small")
    private String keySmall;
    @Column(name = "is_arhiv")
    private boolean isArhiv;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public FileEntity(String name, Long size, String key, String keySmall, LocalDateTime uploadDate) {
        this.name = name;
        this.size = size;
        this.key = key;
        this.keySmall = keySmall;
        this.uploadDate = uploadDate;
    }
}
