package com.delivery_man.entity;

import com.delivery_man.dto.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    public Order(OrderCreateRequestDto dto, BigDecimal price) {
        this.status = "use";
        this.totalPrice = price;
    }
}
