package com.example.sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "items_menu")
public class ItemsMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EModulesMenu name;
    private Boolean isEnabled;
    private String nametwo;

    public ItemsMenu(EModulesMenu name) {
        this.name = name;
    }

    public ItemsMenu(EModulesMenu name, Boolean isEnabled, String nametwo) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.nametwo = nametwo;
    }
}
