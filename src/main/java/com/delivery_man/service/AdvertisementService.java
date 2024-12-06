package com.delivery_man.service;

import com.delivery_man.dto.AdvertisementCreateRequestDto;
import com.delivery_man.dto.AdvertisementResponseDto;

public interface AdvertisementService {
    AdvertisementResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto);
}
