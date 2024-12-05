package com.delivery_man.controller;


import com.delivery_man.config.Const;
import com.delivery_man.dto.Authentication;
import com.delivery_man.dto.CartCreateRequestDto;
import com.delivery_man.dto.CartResponseDto;
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
    public ResponseEntity<CartResponseDto> create (@PathVariable("userId") Long userId,
                                                         @Valid @RequestBody CartCreateRequestDto dto,
                                                         @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication){
        dto.updateIds(userId, authentication.getId());

        return new ResponseEntity<>(cartService.create(dto), HttpStatus.OK);

    }
}

