package com.delivery_man.dto.shop;

import com.delivery_man.dto.menu.MenuResponseDto;
import com.delivery_man.dto.review.ReviewResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ShopFindOneResponseDto {
    private final ShopResponseDto shop;
    private final List<MenuResponseDto> menus;
    private final List<ReviewResponseDto> review;
}
