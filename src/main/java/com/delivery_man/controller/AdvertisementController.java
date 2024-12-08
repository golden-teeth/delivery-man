package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.advertisement.AdvertisementApplyRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementApplyResponseDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateResponseDto;
import com.delivery_man.model.dto.auth.Authentication;
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
            @PathVariable Long advertisementId,
            @RequestBody AdvertisementApplyRequestDto dto,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        AdvertisementApplyResponseDto advertisementApplyResponseDto = advertisementService.applyAdvertisement(advertisementId, sessionId,dto);
        return new ResponseEntity<>(advertisementApplyResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{shopId}/{advertisementId}")
    public ResponseEntity<AdvertisementApplyResponseDto> getAdvertisement(
            @PathVariable Long shopId,
            @PathVariable Long advertisementId,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        AdvertisementApplyResponseDto advertisementApplyResponseDto = advertisementService.findAdvertisement(shopId, advertisementId, sessionId);
        return new ResponseEntity<>(advertisementApplyResponseDto, HttpStatus.OK);
    }

}
