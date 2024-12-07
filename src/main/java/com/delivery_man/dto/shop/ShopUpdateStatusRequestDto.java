package com.delivery_man.dto.shop;

import com.delivery_man.constant.ShopStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ShopUpdateStatusRequestDto {
    @NotNull(message = "가게 상태를 입력해주세요")
    private final ShopStatus status;

    public ShopUpdateStatusRequestDto(ShopStatus status) {
        this.status = status;
    }
}
