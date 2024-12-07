package com.delivery_man.service;

import com.delivery_man.dto.order.OrderCreateRequestDto;
import com.delivery_man.dto.order.OrderResponseDto;
import com.delivery_man.dto.order.OrderUpdateRequestDto;

public interface OrderService {
    OrderResponseDto create(OrderCreateRequestDto dto);

    OrderResponseDto update( OrderUpdateRequestDto dto);
}
