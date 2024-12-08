package com.delivery_man.model.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateRequestDto {
    @NotBlank
    private String status;

    private Long orderId;
    private Long userId;

    public void updateIds(Long orderId, Long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

}
