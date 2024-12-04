package com.delivery_man.entity;

import com.delivery_man.enums.ClosedDays;
import com.delivery_man.enums.ShopStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "shop")
public class Shop extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal minimumPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShopStatus status;

    @Column(nullable = false)
    private String openAt;

    @Column(nullable = false)
    private String closeAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClosedDays closedDays;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Shop() {
    }

    public Shop(String name, BigDecimal minimumPrice, ShopStatus status, String openAt, String closeAt, ClosedDays closedDays, User user) {
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.closedDays = closedDays;
        this.user = user;
    }
}
