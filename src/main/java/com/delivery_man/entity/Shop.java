package com.delivery_man.entity;

import com.delivery_man.constant.ClosedDays;
import com.delivery_man.constant.ShopStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy="shop", cascade = CascadeType.REMOVE)
    private final List<Menu> menus = new ArrayList<Menu>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
    private final List<Order> orders = new ArrayList<>();

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

    public void updateStatus(ShopStatus status){
        this.status = status;
    }
}
