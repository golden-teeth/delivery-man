package com.delivery_man.service;

import com.delivery_man.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    ShopResponseDto createShop(ShopCreateRequestDto dto, Long sessionId) ;

    List<ShopResponseDto> findAllShops(String shopName);

    ShopFindOneResponseDto findShop(Long shopId);

    ShopUpdateStatusResponseDto updateShopStatus(ShopUpdateStatusRequestDto dto, Long shopId);
}
