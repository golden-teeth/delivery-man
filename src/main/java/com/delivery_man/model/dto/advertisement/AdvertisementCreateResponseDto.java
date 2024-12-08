package com.delivery_man.model.dto.advertisement;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AdvertisementCreateResponseDto {
    private final Long id;
    private final BigDecimal bid;
    private final String status;

    public AdvertisementCreateResponseDto(Long id, BigDecimal bid, String status) {
        this.id = id;
        this.bid = bid;
        this.status = status;
    }
}
