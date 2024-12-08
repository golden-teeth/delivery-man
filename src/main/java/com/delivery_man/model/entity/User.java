package com.delivery_man.model.entity;

import com.delivery_man.utils.PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "user")
public class User extends CreateAndUpdateDateEntity {

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

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    // Reviews, Cart

    public User() {
    }

    public User(String email, String password, String grade, String name) {
        this.email = email;
        this.password = PasswordEncoder.encode(password);
        this.grade = grade;
        this.name = name;
        this.status = "normal";
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
