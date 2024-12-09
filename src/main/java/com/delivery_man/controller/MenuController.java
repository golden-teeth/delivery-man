package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.model.dto.menu.MenuCreateRequestDto;
import com.delivery_man.model.dto.menu.MenuResponseDto;
import com.delivery_man.model.dto.menu.MenuUpdateRequestDto;
import com.delivery_man.model.dto.menu.MenuWithPictureResponseDto;
import com.delivery_man.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops/{shopId}/menus")
public class MenuController {
    private final MenuService service;

    @PostMapping
    public ResponseEntity<MenuWithPictureResponseDto> create(@PathVariable("shopId") Long shopId,
                                                             @Valid @RequestPart MenuCreateRequestDto dto,
                                                             @RequestPart(value = "images", required = false) MultipartFile image,
                                                             @SessionAttribute(name = Const.SESSION_KEY)Authentication authentication
                                                  ) throws IOException {
        dto.setIds(authentication.getId(), shopId);
        MenuWithPictureResponseDto responseDto = service.create(dto, image);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> update(@PathVariable("shopId") Long shopId,
                                                  @PathVariable("menuId") Long menuId,
                                                  @Valid @RequestBody MenuUpdateRequestDto dto,
                                                  @SessionAttribute(name = Const.SESSION_KEY)Authentication authentication) {
        dto.setIds(authentication.getId(), shopId, menuId);
        MenuResponseDto responseDto = service.update(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable("shopId") Long shopId,
                                       @PathVariable("menuId") Long menuId,
                                       @SessionAttribute(name = Const.SESSION_KEY)Authentication authentication) {
        service.delete(shopId, menuId,authentication.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
