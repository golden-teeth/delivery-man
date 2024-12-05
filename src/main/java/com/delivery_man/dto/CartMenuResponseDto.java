package com.delivery_man.dto;

import com.delivery_man.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartMenuResponseDto {

    private Long menuId;
    private int quantity;

    public CartMenuResponseDto(Cart cart) {
        this.menuId = cart.getMenu().getId();
        this.quantity = cart.getQuantity();
    }
}
