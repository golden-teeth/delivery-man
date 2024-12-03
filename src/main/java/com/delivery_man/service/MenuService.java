package com.delivery_man.service;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuResponseDto;

public interface MenuService {

    MenuResponseDto create(MenuCreateRequestDto dto);
}
