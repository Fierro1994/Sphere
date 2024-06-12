package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "nav_modules")
public class NavModules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ENavModules name;
    private Boolean isEnabled;
    private String nametwo;

    public NavModules(ENavModules name) {
        this.name = name;
    }

    public NavModules(ENavModules name, Boolean isEnabled, String nametwo) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.nametwo = nametwo;
    }
}
