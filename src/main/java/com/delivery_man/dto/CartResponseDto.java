package com.delivery_man.dto;

import com.delivery_man.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private Long shopId;

    private List<CartMenuResponseDto> cartMenuResponseDtoList;

    public CartResponseDto(Cart cart) {
        this.shopId = cart.getMenu().getShop().getId();
        this.cartMenuResponseDtoList = new ArrayList<>();
        this.cartMenuResponseDtoList.add(new CartMenuResponseDto(cart));
    }

    public CartResponseDto(List<Cart> cartList) {
        this.shopId = cartList.get(0).getMenu().getShop().getId();
        this.cartMenuResponseDtoList = new ArrayList<>();
        for (Cart cart : cartList) {
            this.cartMenuResponseDtoList.add(new CartMenuResponseDto(cart));
        }
    }
}
