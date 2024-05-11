package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class InfoModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String infotext;
    private int block;

    public InfoModule(String name, String infotext, int block) {
        this.name = name;
        this.infotext = infotext;
        this.block = block;
    }
}
