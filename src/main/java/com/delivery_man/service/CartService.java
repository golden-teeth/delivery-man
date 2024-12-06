package com.delivery_man.service;

import com.delivery_man.dto.CartCreateRequestDto;
import com.delivery_man.dto.CartResponseDto;
import com.delivery_man.dto.UserValidDto;

public interface CartService {
    CartResponseDto create( CartCreateRequestDto dto);

    CartResponseDto find(Long userId, Long id);

    CartResponseDto deleteByMenuId(UserValidDto userValidDto, Long menuId);
}
