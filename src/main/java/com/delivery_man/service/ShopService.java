package com.delivery_man.service;

import com.delivery_man.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopService {
    ShopWithPictureResponseDto createShop(ShopCreateRequestDto dto, Long sessionId, MultipartFile image) throws IOException;

    List<ShopResponseDto> findAllShops(String shopName, Long sessionId);

    ShopFindOneResponseDto findShop(String sort, int page, int size, int ratingMin, int ratingMax, Long shopId, Long sessionId);

    ShopUpdateStatusResponseDto updateShopStatus(ShopUpdateStatusRequestDto dto, Long shopId);
}
