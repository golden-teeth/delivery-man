package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.AdErrorCode;
import com.delivery_man.constant.errorcode.ShopErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.dto.advertisement.AdvertisementApplyRequestDto;
import com.delivery_man.dto.advertisement.AdvertisementApplyResponseDto;
import com.delivery_man.dto.advertisement.AdvertisementCreateRequestDto;
import com.delivery_man.dto.advertisement.AdvertisementCreateResponseDto;
import com.delivery_man.entity.Advertisement;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.repository.AdvertisementRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final ShopRepository shopRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    @Override
    public AdvertisementCreateResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        if(!findShop.getUser().getId().equals(sessionId)){
            throw new ApiException(AdErrorCode.INVALID_SHOP_AD);
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
    public AdvertisementApplyResponseDto applyAdvertisement(Long advertisementId, Long sessionId, AdvertisementApplyRequestDto dto) {
        Advertisement findAd = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ApiException(AdErrorCode.AD_NOT_FOUND));

        findAd.applyAd(dto.getStatus(),dto.getRejectReason(),sessionId);
        Advertisement applyAd = advertisementRepository.save(findAd);

        return new AdvertisementApplyResponseDto(
                applyAd.getId(), applyAd.getBid(),applyAd.getStatus(),applyAd.getRejectReason(),applyAd.getAdminId(), applyAd.getAppliedAt());
    }

    /**
     * @param shopId
     * @param advertisementId
     * @param sessionId
     * @return
     */
    @Override
    public AdvertisementApplyResponseDto findAdvertisement(Long shopId, Long advertisementId, Long sessionId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        Advertisement findAd = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ApiException(AdErrorCode.AD_NOT_FOUND));

        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if(!findAd.getShop().getUser().getId().equals(sessionId) || findUser.getGrade().equals("admin")){
            throw new ApiException(AdErrorCode.INVALID_SHOP_AD);
        }

        return new AdvertisementApplyResponseDto(
                findAd.getId(),findAd.getBid(), findAd.getStatus(), findAd.getRejectReason(),findAd.getAdminId(),findAd.getAppliedAt()
        );
    }
}
