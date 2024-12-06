package com.delivery_man.dto;

import lombok.Getter;

@Getter
public class AdvertisementApplyRequestDto {
    private final String status;
    private final String rejectReason;

    public AdvertisementApplyRequestDto(String status, String rejectReason) {
        this.status = status;
        this.rejectReason = rejectReason;
    }
}
