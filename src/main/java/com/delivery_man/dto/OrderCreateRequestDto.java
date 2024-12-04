package com.delivery_man.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    @NotBlank
    private Long menuId;

    private Long userId;

    public void updateIds(Long userId) {
        this.userId = userId;
    }
}
