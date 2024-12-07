package com.delivery_man.service;

import com.delivery_man.dto.shop.*;

import java.util.List;

public interface ShopService {
    ShopResponseDto createShop(ShopCreateRequestDto dto, Long sessionId) ;

    List<ShopResponseDto> findAllShops(String shopName, Long sessionId);

    ShopFindOneResponseDto findShop(String sort, int page, int size, int ratingMin, int ratingMax, Long shopId, Long sessionId);

    ShopUpdateStatusResponseDto updateShopStatus(ShopUpdateStatusRequestDto dto, Long shopId);
}
