package com.example.Sphere.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "module_page")
public class MainPageModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EMainPageModules name;
    private Boolean isEnabled;
    private String nametwo;
    private String color;
    private List<String> pathImage;

    public MainPageModule(EMainPageModules name) {
        this.name = name;
    }

    public MainPageModule(EMainPageModules name, Boolean isEnabled, String nametwo) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.nametwo = nametwo;
    }

    public MainPageModule(EMainPageModules name, Boolean isEnabled, String nametwo, List<String> pathImage) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.nametwo = nametwo;
        this.pathImage = pathImage;
    }
}
