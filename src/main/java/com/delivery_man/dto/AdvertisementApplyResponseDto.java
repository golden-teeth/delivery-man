package com.delivery_man.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AdvertisementApplyResponseDto {
    private final Long id;
    private final BigDecimal bid;
    private final String status;
    private final String rejectReason;
    private final Long adminId;
    private final LocalDateTime appliedAt;

    public AdvertisementApplyResponseDto(Long id, BigDecimal bid, String status, String rejectReason, Long adminId, LocalDateTime appliedAt) {
        this.id = id;
        this.bid = bid;
        this.status = status;
        this.rejectReason = rejectReason;
        this.adminId = adminId;
        this.appliedAt = appliedAt;
    }
}
