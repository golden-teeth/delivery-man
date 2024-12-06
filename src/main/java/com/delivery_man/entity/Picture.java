package com.delivery_man.entity;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Picture() {
    }

    public Picture(String imagePath, User user) {
        this.imagePath = imagePath;
        this.user = user;
    }
}
