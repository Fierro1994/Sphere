package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "moments")
public class Moments{
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
    private String category;
    @Column(name = "is_actual")
    private boolean isActual;
    @Column(name = "is_arhiv")
    private boolean isArhiv;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public Moments(String name, String format, Long size, String key, String category, boolean isActual, boolean isArhiv, LocalDateTime uploadDate) {
        this.name = name;
        this.format = format;
        this.size = size;
        this.key = key;
        this.category = category;
        this.isActual = isActual;
        this.isArhiv = isArhiv;
        this.uploadDate = uploadDate;
    }

    public Moments(String name, Long size, String format, String key, LocalDateTime uploadDate) {
        this.name = name;
        this.size = size;
        this.format = format;
        this.key = key;
        this.uploadDate = uploadDate;
    }


}
