package com.example.Sphere.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import lombok.*;

@Entity
@Table(name = "Roles")
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}