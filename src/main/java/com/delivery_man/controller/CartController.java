package com.delivery_man.controller;


import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.model.dto.cart.CartCreateRequestDto;
import com.delivery_man.model.dto.cart.CartResponseDto;
import com.delivery_man.model.dto.user.UserValidDto;
import com.delivery_man.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/users/{userId}/carts")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDto> create(@PathVariable("userId") Long userId,
                                                  @Valid @RequestBody CartCreateRequestDto dto,
                                                  @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication) {
        dto.updateIds(userId, authentication.getId());

        return new ResponseEntity<>(cartService.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> find(@PathVariable("userId") Long userId,
                                                @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication) {
        return new ResponseEntity<>(cartService.find(userId, authentication.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<CartResponseDto> deleteByMenuId(@PathVariable("userId") Long userId,
                                                          @PathVariable("menuId") Long menuId,
                                                      @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication) {
        UserValidDto userValidDto = new UserValidDto(userId,authentication.getId());
        return new ResponseEntity<>(cartService.deleteByMenuId(userValidDto,menuId), HttpStatus.OK);
    }
}


