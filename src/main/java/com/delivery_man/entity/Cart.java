package com.delivery_man.entity;

import com.delivery_man.dto.CartCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Cart extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void updateByDto(CartCreateRequestDto dto) {
        this.quantity = dto.getQuantity();
    }

    public void updateMenu(Menu menu) {
        this.menu = menu;
    }

    public void updateUser(User user) {
        this.user = user;

    }
}
