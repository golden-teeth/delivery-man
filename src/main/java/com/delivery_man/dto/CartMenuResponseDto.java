package com.delivery_man.dto;

import com.delivery_man.entity.Menu;
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
