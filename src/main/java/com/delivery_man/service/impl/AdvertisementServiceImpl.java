package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.AdErrorCode;
import com.delivery_man.constant.ShopErrorCode;
import com.delivery_man.dto.AdvertisementApplyRequestDto;
import com.delivery_man.dto.AdvertisementApplyResponseDto;
import com.delivery_man.dto.AdvertisementCreateRequestDto;
import com.delivery_man.dto.AdvertisementCreateResponseDto;
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
    public AdvertisementCreateResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        if(!findShop.getUser().getId().equals(sessionId)){
            throw new ApiException(ShopErrorCode.INVALID_SHOP_AD);
        }

        Advertisement advertisement = new Advertisement(dto.getBid(),findShop);
        Advertisement createAd = advertisementRepository.save(advertisement);

        return new AdvertisementCreateResponseDto(createAd.getId(),createAd.getBid(),createAd.getStatus());
    }

    /**
     * @param shopId
     * @param sessionId
     * @param dto
     * @return
     */
    @Override
    public AdvertisementApplyResponseDto applyAdvertisement(Long shopId, Long advertisementId, Long sessionId, AdvertisementApplyRequestDto dto) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        Advertisement findAd = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ApiException(AdErrorCode.AD_NOT_FOUND));

        findAd.applyAd(dto.getStatus(),dto.getRejectReason(),sessionId);
        Advertisement applyAd = advertisementRepository.save(findAd);

        return new AdvertisementApplyResponseDto(
                applyAd.getId(), applyAd.getBid(),applyAd.getStatus(),applyAd.getRejectReason(),applyAd.getAdminId(), applyAd.getAppliedAt());
    }
}
