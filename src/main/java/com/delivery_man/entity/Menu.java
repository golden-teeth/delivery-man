package com.delivery_man.entity;

import com.delivery_man.dto.MenuCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor
public class Menu extends BaseEntity {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String status;


    public Menu(MenuCreateRequestDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.status="use";
    }
}
