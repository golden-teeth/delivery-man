package com.delivery_man.model.dto.cart;

import com.delivery_man.model.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartMenuResponseDto {

    private Long menuId;
    private int quantity;

    public CartMenuResponseDto(Menu menu, int quantity) {
        this.menuId = menu.getId();
        this.quantity = quantity;
    }
}
