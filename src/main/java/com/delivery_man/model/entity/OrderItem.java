package com.delivery_man.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Getter
public class OrderItem extends CreateDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Cart cart) {
        this.name = cart.getMenu().getName();
        this.price = cart.getMenu().getPrice();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
