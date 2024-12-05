package com.delivery_man.service;

import com.delivery_man.dto.CartCreateRequestDto;
import com.delivery_man.dto.CartResponseDto;

public interface CartService {
    CartResponseDto create( CartCreateRequestDto dto);
}
