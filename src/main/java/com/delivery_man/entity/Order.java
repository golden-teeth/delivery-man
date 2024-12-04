package com.delivery_man.entity;

import com.delivery_man.dto.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
}
