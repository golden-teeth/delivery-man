package com.delivery_man.model.dto.shop;

import com.delivery_man.constant.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ShopCreateRequestDto {

    @NotBlank(message = "가게 이름을 입력해주세요")
    private final String name;

    @NotNull(message = "최소 주문 금액을 입력해주세요.")
    private final BigDecimal minimumPrice;

    @NotNull(message = "가게 상태를 입력해주세요")
    private final ShopStatus status;

    @NotBlank(message = "오픈 시간을 입력해주세요")
    private final String openAt;

    @NotBlank(message = "마감 시간을 입력해주세요")
    private final String closeAt;

    @NotNull(message = "휴무일을 입력해주세요")
    private final List<String> closedDays;
    
    public ShopCreateRequestDto(String name, BigDecimal minimumPrice, ShopStatus status, String openAt, String closeAt, List<String> closedDays) {
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.closedDays = closedDays;
    }
}
