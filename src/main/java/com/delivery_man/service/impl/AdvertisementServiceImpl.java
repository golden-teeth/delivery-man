package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.ShopErrorCode;
import com.delivery_man.dto.AdvertisementCreateRequestDto;
import com.delivery_man.dto.AdvertisementResponseDto;
import com.delivery_man.entity.Advertisement;
import com.delivery_man.entity.Shop;
import com.delivery_man.repository.AdvertisementRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final ShopRepository shopRepository;
    private final AdvertisementRepository advertisementRepository;

    @Override
    public AdvertisementResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        if(!findShop.getUser().getId().equals(sessionId)){
            throw new ApiException(ShopErrorCode.INVALID_SHOP_AD);
        }

        Advertisement advertisement = new Advertisement(dto.getBid(),findShop);
        Advertisement createAd = advertisementRepository.save(advertisement);

        return new AdvertisementResponseDto(createAd.getId(),createAd.getBid(),createAd.getStatus());
    }
}
