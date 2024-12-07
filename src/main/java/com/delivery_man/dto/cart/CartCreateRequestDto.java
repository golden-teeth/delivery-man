package com.delivery_man.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartCreateRequestDto {
    @Null
    private Long userId;
    @Null
    private Long authUserId;
    @NotNull
    private Long shopId;

    @NotNull
    private Long menuId;

    @NotNull
    @Positive
    private int quantity;

    public void updateIds(Long userId, Long authUserId) {
        this.userId = userId;
        this.authUserId = authUserId;
    }
}
