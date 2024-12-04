package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.ShopCreateRequestDto;
import com.delivery_man.dto.ShopResponseDto;
import com.delivery_man.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(
           @Valid @RequestBody ShopCreateRequestDto dto
           //@SessionAttribute(name = Const.SESSION_KEY) Long sessionId

    ){
        Long sessionId=1L;
        ShopResponseDto shopResponseDto = shopService.createShop(dto, sessionId);
        return new ResponseEntity<>(shopResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> findAllShops(
            @RequestParam String shopName
    ){
        List<ShopResponseDto> shopResponseDtos = shopService.findAllShops(shopName);
        return new ResponseEntity<>(shopResponseDtos,HttpStatus.OK);
    }
}
