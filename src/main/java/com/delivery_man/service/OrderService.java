package com.delivery_man.service;

import com.delivery_man.dto.OrderCreateRequestDto;
import com.delivery_man.dto.OrderResponseDto;
import com.delivery_man.dto.OrderUpdateRequestDto;

public interface OrderService {
    OrderResponseDto create(OrderCreateRequestDto dto);

    OrderResponseDto update( OrderUpdateRequestDto dto);
}
