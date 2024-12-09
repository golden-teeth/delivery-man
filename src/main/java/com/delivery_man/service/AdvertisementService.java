package com.delivery_man.service;

import com.delivery_man.model.dto.advertisement.AdvertisementApplyRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementApplyResponseDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateResponseDto;

public interface AdvertisementService {
    AdvertisementCreateResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto);
    AdvertisementApplyResponseDto applyAdvertisement(Long advertisementId, Long sessionId, AdvertisementApplyRequestDto dto);

    AdvertisementApplyResponseDto findAdvertisement(Long shopId, Long advertisementId, Long sessionId);
}
