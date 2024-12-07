package com.delivery_man.service;

import com.delivery_man.model.dto.order.OrderCreateRequestDto;
import com.delivery_man.model.dto.order.OrderResponseDto;
import com.delivery_man.model.dto.order.OrderUpdateRequestDto;

public interface OrderService {
    OrderResponseDto create(OrderCreateRequestDto dto);

    OrderResponseDto update( OrderUpdateRequestDto dto);
}
