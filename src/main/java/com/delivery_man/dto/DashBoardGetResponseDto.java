package com.delivery_man.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DashBoardGetResponseDto {
    private final Long orderCount;
    private final BigDecimal orderPriceAmount;
    private final String shopName;
    private String message;

    public DashBoardGetResponseDto(Long orderCount, BigDecimal orderPriceAmount, String shopName) {
        this.orderCount = orderCount;
        this.orderPriceAmount = orderPriceAmount;
        this.shopName = shopName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
