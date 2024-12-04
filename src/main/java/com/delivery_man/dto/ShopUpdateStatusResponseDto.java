package com.delivery_man.dto;

import com.delivery_man.constant.ShopStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShopUpdateStatusResponseDto {
    private final Long id;
    private final String name;
    private final ShopStatus status;
    private final LocalDateTime updatedAt;

    public ShopUpdateStatusResponseDto(Long id, String name, ShopStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
