package com.delivery_man.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String grade;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 10)
    private String status;

    public User() {
    }

    public User(Long id, String email, String password, String grade, String name, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.grade = grade;
        this.name = name;
        this.status = status;
    }
}
