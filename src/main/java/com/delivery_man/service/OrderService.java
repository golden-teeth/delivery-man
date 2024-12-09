package com.delivery_man.service;

import com.delivery_man.model.dto.order.OrderResponseDto;
import com.delivery_man.model.dto.order.OrderUpdateRequestDto;
import com.delivery_man.model.dto.user.UserValidDto;

public interface OrderService {
    OrderResponseDto create(UserValidDto dto);

    OrderResponseDto update( OrderUpdateRequestDto dto);
}
