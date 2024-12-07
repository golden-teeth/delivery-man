package com.delivery_man.dto.cart;

import com.delivery_man.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private Long shopId;

    private List<CartMenuResponseDto> carts;

    public CartResponseDto(Cart cart) {
        this.shopId = cart.getMenu().getShop().getId();
        this.carts = new ArrayList<>();
        this.carts.add(new CartMenuResponseDto(cart.getMenu(),cart.getQuantity()));
    }

    public CartResponseDto(List<Cart> cartList) {
        this.shopId = cartList.get(0).getMenu().getShop().getId();
        this.carts = new ArrayList<>();
        for (Cart cart : cartList) {
            carts.add(new CartMenuResponseDto(cart.getMenu(),cart.getQuantity()));
        }
    }

}
