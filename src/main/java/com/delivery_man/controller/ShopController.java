package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.*;
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

    /**
     * 가게 생성 API
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(
           @Valid @RequestBody ShopCreateRequestDto dto,
           @SessionAttribute(name = Const.SESSION_KEY) Authentication session
    ){
        Long sessionId = session.getId();
        ShopResponseDto shopResponseDto = shopService.createShop(dto, sessionId);
        return new ResponseEntity<>(shopResponseDto, HttpStatus.CREATED);
    }

    /**
     * 가게 목록 조회 API
     * @param shopName
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> findAllShops(
            @RequestParam String shopName
    ){
        List<ShopResponseDto> shopResponseDtos = shopService.findAllShops(shopName);
        return new ResponseEntity<>(shopResponseDtos,HttpStatus.OK);
    }

    /**
     * 가게 상세 조회 API
     * @param shopId
     * @return
     */
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopFindOneResponseDto> findShop(
            @PathVariable Long shopId
    ){
        ShopFindOneResponseDto shopFindOneResponseDto = shopService.findShop(shopId);
        return new ResponseEntity<>(shopFindOneResponseDto,HttpStatus.OK);
    }


    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopUpdateStatusResponseDto> updateShopStatus(
            @PathVariable Long shopId,
            @RequestBody ShopUpdateStatusRequestDto dto
            ){
        ShopUpdateStatusResponseDto shopUpdateStatusResponseDto = shopService.updateShopStatus(dto,shopId);
        return new ResponseEntity<>(shopUpdateStatusResponseDto,HttpStatus.OK);
    }

}
