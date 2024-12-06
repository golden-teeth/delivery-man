package com.delivery_man.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
