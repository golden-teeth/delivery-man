package com.delivery_man.dto;

import com.delivery_man.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long userId;
    private Long menuId;
    private LocalDateTime updateAt;
    private String status;

    public OrderResponseDto(Order order) {
        this.userId = order.getUser().getId();
        this.menuId = order.getMenu().getId();
        this.updateAt = order.getUpdatedAt();
        this.status = order.getStatus();

    }
}
