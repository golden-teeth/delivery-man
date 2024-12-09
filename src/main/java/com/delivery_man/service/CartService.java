package com.delivery_man.service;

import com.delivery_man.model.dto.cart.CartCreateRequestDto;
import com.delivery_man.model.dto.cart.CartResponseDto;
import com.delivery_man.model.dto.user.UserValidDto;

public interface CartService {
    CartResponseDto create( CartCreateRequestDto dto);

    CartResponseDto find(Long userId, Long id);

    CartResponseDto deleteByMenuId(UserValidDto userValidDto, Long menuId);
}
