package com.delivery_man.model.dto.order;

import com.delivery_man.model.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long userId;
    private Long orderId;
    private LocalDateTime updateAt;
    private String status;

    public OrderResponseDto(Order order) {
        this.userId = order.getUser().getId();
        this.orderId = order.getId();
        this.updateAt = order.getUpdatedAt();
        this.status = order.getStatus();

    }
}
