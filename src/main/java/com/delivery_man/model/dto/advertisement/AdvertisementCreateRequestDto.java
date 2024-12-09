package com.delivery_man.model.dto.advertisement;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AdvertisementCreateRequestDto {
    private BigDecimal bid;

    public AdvertisementCreateRequestDto(BigDecimal bid) {
        this.bid = bid;
    }
}
