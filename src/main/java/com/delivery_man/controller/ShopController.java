package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.model.dto.shop.*;
import com.delivery_man.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<ShopWithPictureResponseDto> createShop(
           @Valid @RequestPart ShopCreateRequestDto dto,
           @RequestPart(value = "images", required = false) MultipartFile image,
           @SessionAttribute(name = Const.SESSION_KEY) Authentication session
    ) throws IOException {
        Long sessionId = session.getId();
        ShopWithPictureResponseDto shopWithPictureResponseDto = shopService.createShop(dto, sessionId, image);
        return new ResponseEntity<>(shopWithPictureResponseDto, HttpStatus.CREATED);
    }

    /**
     * 가게 목록 조회 API
     * @param shopName
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> findAllShops(
            @RequestParam String shopName,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication session
    ){
        Long sessionId = session.getId();
        List<ShopResponseDto> shopResponseDtos = shopService.findAllShops(shopName,sessionId);
        return new ResponseEntity<>(shopResponseDtos,HttpStatus.OK);
    }

    /**
     * 가게 상세 조회 API
     * @param shopId
     * @return
     */
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopFindOneResponseDto> findShop(
            @PathVariable Long shopId,
            @RequestParam(required = false, defaultValue = "CREATE") String sort,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "1") int ratingMin,
            @RequestParam(required = false, defaultValue = "5") int ratingMax,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication session
    ){
        Long sessionId = session.getId();
        ShopFindOneResponseDto shopFindOneResponseDto = shopService.findShop(sort, page, size, ratingMin, ratingMax, shopId, sessionId);
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
