package com.delivery_man.dto;

import com.delivery_man.constant.ShopStatus;
import com.delivery_man.entity.Shop;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public class ShopWithPictureResponseDto {
    private final Long id;

    private final String name;

    private final BigDecimal minimumPrice;

    private final ShopStatus status;

    private final String openAt;

    private final String closeAt;

    private final List<String> closedDays;

    private final LocalDateTime updatedAt;

    private final Long userId;

    private final String publicUrl;

    public ShopWithPictureResponseDto(Long id, String name, BigDecimal minimumPrice, ShopStatus status, String openAt, String closeAt, List<String> closedDays, LocalDateTime updatedAt, Long userId, String publicUrl) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.closedDays = closedDays;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.publicUrl = publicUrl;
    }

    public ShopWithPictureResponseDto(Shop shop, String publicUrl) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.minimumPrice = shop.getMinimumPrice();
        this.status = shop.getStatus();
        this.openAt = shop.getOpenAt();
        this.closeAt = shop.getCloseAt();
        this.closedDays = Collections.singletonList(shop.getClosedDays());
        this.updatedAt = shop.getUpdatedAt();
        this.userId = shop.getUser().getId();
        this.publicUrl = publicUrl;
    }
}
