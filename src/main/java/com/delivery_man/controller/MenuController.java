package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.auth.Authentication;
import com.delivery_man.dto.menu.MenuCreateRequestDto;
import com.delivery_man.dto.menu.MenuResponseDto;
import com.delivery_man.dto.menu.MenuUpdateRequestDto;
import com.delivery_man.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops/{shopId}/menus")
public class MenuController {
    private final MenuService service;

    @PostMapping
    public ResponseEntity<MenuResponseDto> create(@PathVariable("shopId") Long shopId,
                                                  @Valid @RequestBody MenuCreateRequestDto dto,
                                                  @SessionAttribute(name = Const.SESSION_KEY)Authentication authentication
                                                  ) {
        dto.setIds(authentication.getId(), shopId);
        MenuResponseDto responseDto = service.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
