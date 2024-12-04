package com.delivery_man.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    @NotNull
    private Long menuId;

    private Long userId;

    public void updateIds(Long userId) {
        this.userId = userId;
    }
}
