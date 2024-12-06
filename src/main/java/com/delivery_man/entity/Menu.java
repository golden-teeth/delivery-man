package com.delivery_man.entity;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menu")
public class Menu extends CreateAndUpdateDateEntity {
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

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;


    public Menu(MenuCreateRequestDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.status="use";
    }

    public void update(MenuUpdateRequestDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();

    }


    public void delete() {
        this.status="delete";
    }

    public void updateShop(Shop shop) {
        this.shop = shop;
    }
}
