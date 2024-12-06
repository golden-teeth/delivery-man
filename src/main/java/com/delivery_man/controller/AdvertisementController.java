package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.*;
import com.delivery_man.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/advertisement"})
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("/{shopId}")
    public ResponseEntity<AdvertisementCreateResponseDto> createAdvertisement(
            @PathVariable Long shopId,
            @RequestBody AdvertisementCreateRequestDto dto,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        AdvertisementCreateResponseDto advertisementCreateResponseDto = advertisementService.createAdvertisement(shopId, sessionId, dto);
        return new ResponseEntity<>(advertisementCreateResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{shopId}/{advertisementId}/apply")
    public ResponseEntity<AdvertisementApplyResponseDto> applyAdvertisement(
            @PathVariable Long shopId,
            @PathVariable Long advertisementId,
            @RequestBody AdvertisementApplyRequestDto dto,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        AdvertisementApplyResponseDto advertisementApplyResponseDto = advertisementService.applyAdvertisement(shopId,advertisementId, sessionId,dto);
        return new ResponseEntity<>(advertisementApplyResponseDto, HttpStatus.OK);
    }

}
