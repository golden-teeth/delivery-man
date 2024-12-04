package com.delivery_man.dto;

import com.delivery_man.entity.Shop;
import com.delivery_man.constant.ClosedDays;
import com.delivery_man.constant.ShopStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ShopResponseDto {
    private final Long id;

    private final String name;

    private final BigDecimal minimumPrice;

    private final ShopStatus status;

    private final String openAt;

    private final String closeAt;

    private final ClosedDays closedDays;

    private final LocalDateTime updatedAt;

    private final Long userId;

    public ShopResponseDto(Long id, String name, BigDecimal minimumPrice, ShopStatus status, String openAt, String closeAt, ClosedDays closedDays, LocalDateTime updatedAt, Long userId) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.closedDays = closedDays;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public ShopResponseDto(Shop shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.minimumPrice = shop.getMinimumPrice();
        this.status = shop.getStatus();
        this.openAt = shop.getOpenAt();
        this.closeAt = shop.getCloseAt();
        this.closedDays = shop.getClosedDays();
        this.updatedAt = shop.getUpdatedAt();
        this.userId = shop.getUser().getId();
    }
}
