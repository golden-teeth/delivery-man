package com.delivery_man.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
public class AdvertisementCreateRequestDto {
    private BigDecimal bid;

    public AdvertisementCreateRequestDto(BigDecimal bid) {
        this.bid = bid;
    }
}
