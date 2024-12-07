package com.delivery_man.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "point")
public class Point extends CreateDateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal point;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updatePoint(BigDecimal point, User user) {
        this.point = point;
        this.user = user;
    }
}
