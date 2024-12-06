package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.AdvertisementCreateRequestDto;
import com.delivery_man.dto.AdvertisementResponseDto;
import com.delivery_man.dto.Authentication;
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
    public ResponseEntity<AdvertisementResponseDto> createAdvertisement(
            @PathVariable Long shopId,
            @RequestBody AdvertisementCreateRequestDto dto,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        AdvertisementResponseDto advertisementResponseDto = advertisementService.createAdvertisement(shopId, sessionId, dto);
        return new ResponseEntity<>(advertisementResponseDto, HttpStatus.CREATED);
    }

}
