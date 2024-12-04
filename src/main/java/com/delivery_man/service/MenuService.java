package com.delivery_man.service;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuResponseDto;
import com.delivery_man.dto.MenuUpdateRequestDto;

public interface MenuService {

    MenuResponseDto create(MenuCreateRequestDto dto);

    MenuResponseDto update(MenuUpdateRequestDto dto);
}
