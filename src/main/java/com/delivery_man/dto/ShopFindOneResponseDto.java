package com.delivery_man.dto;

import com.delivery_man.entity.Menu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ShopFindOneResponseDto {
    private final ShopResponseDto shop;
    private final List<MenuResponseDto> menus;
}
