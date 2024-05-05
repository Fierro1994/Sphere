package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "moments")
public class Moments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialId;
    private String path;
    private String article;
    private LocalDateTime createTime;

    public Moments(String serialId, String path, String article, LocalDateTime createTime) {
        this.serialId = serialId;
        this.path = path;
        this.article = article;
        this.createTime = createTime;
    }
}
