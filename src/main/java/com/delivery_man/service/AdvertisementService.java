package com.delivery_man.service;

import com.delivery_man.dto.AdvertisementApplyRequestDto;
import com.delivery_man.dto.AdvertisementApplyResponseDto;
import com.delivery_man.dto.AdvertisementCreateRequestDto;
import com.delivery_man.dto.AdvertisementCreateResponseDto;

public interface AdvertisementService {
    AdvertisementCreateResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto);
    AdvertisementApplyResponseDto applyAdvertisement(Long shopId, Long advertisementId, Long sessionId, AdvertisementApplyRequestDto dto);
}
