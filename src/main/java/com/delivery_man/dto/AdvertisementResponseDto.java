package com.delivery_man.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AdvertisementResponseDto {
    private final Long id;
    private final BigDecimal bid;
    private final String status;

    public AdvertisementResponseDto(Long id, BigDecimal bid, String status) {
        this.id = id;
        this.bid = bid;
        this.status = status;
    }
}
