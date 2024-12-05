package com.delivery_man.entity;

import com.delivery_man.dto.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Review> review = new ArrayList<>();


    public Order(OrderCreateRequestDto dto) {
        this.status = "use";
    }

    public void updateMenu(Menu menu) {
        this.menu = menu;
        this.totalPrice = menu.getPrice();
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateStatus( String status) {
        this.status = status;
    }

    public void updateShop(Shop shop){this.shop = shop;}

}
