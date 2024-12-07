package com.delivery_man.service;

import com.delivery_man.dto.menu.MenuCreateRequestDto;
import com.delivery_man.dto.menu.MenuResponseDto;
import com.delivery_man.dto.menu.MenuUpdateRequestDto;

public interface MenuService {

    MenuResponseDto create(MenuCreateRequestDto dto);

    MenuResponseDto update(MenuUpdateRequestDto dto);

    void delete(Long shopId, Long menuId, Long sessionId);

}
