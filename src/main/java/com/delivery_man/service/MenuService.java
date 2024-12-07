package com.delivery_man.service;

import com.delivery_man.dto.MenuCreateRequestDto;
import com.delivery_man.dto.MenuResponseDto;
import com.delivery_man.dto.MenuUpdateRequestDto;
import com.delivery_man.dto.MenuWithPictureResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MenuService {

    MenuWithPictureResponseDto create(MenuCreateRequestDto dto, MultipartFile image) throws IOException;

    MenuResponseDto update(MenuUpdateRequestDto dto);

    void delete(Long shopId, Long menuId, Long sessionId);

}
