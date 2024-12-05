package com.delivery_man.service;

import com.delivery_man.dto.OrderResponseDto;

public interface PointService {

    void createPoint(Long orderId, OrderResponseDto orderResponseDto);
}
