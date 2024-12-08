package com.delivery_man.service;

import com.delivery_man.model.dto.order.OrderResponseDto;

public interface PointService {

    void createPoint(Long orderId, OrderResponseDto orderResponseDto);
}
