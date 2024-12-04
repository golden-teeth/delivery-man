package com.delivery_man.service;

import com.delivery_man.dto.ShopCreateRequestDto;
import com.delivery_man.dto.ShopResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    ShopResponseDto createShop(ShopCreateRequestDto dto, Long sessionId) ;

    List<ShopResponseDto> findAllShops(String shopName);
}
