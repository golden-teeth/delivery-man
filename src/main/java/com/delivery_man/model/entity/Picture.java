package com.delivery_man.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "picture")
public class Picture extends CreateAndUpdateDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long idNumber;

    public Picture() {
    }

    public Picture(String imagePath, String category, Long idNumber) {
        this.imagePath = imagePath;
        this.category = category;
        this.idNumber = idNumber;
    }
}
