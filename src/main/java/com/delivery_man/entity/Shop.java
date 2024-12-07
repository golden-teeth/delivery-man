package com.delivery_man.entity;

import com.delivery_man.constant.ShopStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "shop")
public class Shop extends CreateAndUpdateDateEntity {

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

    private String closedDays;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="shop", cascade = CascadeType.REMOVE)
    private List<Menu> menus;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    public Shop() {
    }

    public Shop(String name, BigDecimal minimumPrice, ShopStatus status, String openAt, String closeAt, List<String> closedDays, User user) {
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.closedDays = String.join(",",closedDays);
        this.user = user;
    }

    public void updateStatus(ShopStatus status){
        this.status = status;
    }
}
