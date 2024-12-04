package com.delivery_man.service;

import com.delivery_man.dto.OrderCreateRequestDto;
import com.delivery_man.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto create(OrderCreateRequestDto dto);
}
