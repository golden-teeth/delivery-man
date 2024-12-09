package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.AdErrorCode;
import com.delivery_man.constant.errorcode.ShopErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.advertisement.AdvertisementApplyRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementApplyResponseDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateRequestDto;
import com.delivery_man.model.dto.advertisement.AdvertisementCreateResponseDto;
import com.delivery_man.model.entity.Advertisement;
import com.delivery_man.model.entity.Shop;
import com.delivery_man.model.entity.User;
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

    /**
     * 광고 요청
     * @param shopId
     * @param sessionId
     * @param dto
     * @return
     */
    @Override
    public AdvertisementCreateResponseDto createAdvertisement(Long shopId, Long sessionId, AdvertisementCreateRequestDto dto) {
        // 가게 확인
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        // 본인의 가게에만 신청할 수 있음
        if(!findShop.getUser().getId().equals(sessionId)){
            throw new ApiException(AdErrorCode.INVALID_SHOP_AD);
        }
        // 가게 입찰가 요청
        Advertisement advertisement = new Advertisement(dto.getBid(),findShop);
        Advertisement createAd = advertisementRepository.save(advertisement);

        return new AdvertisementCreateResponseDto(createAd.getId(),createAd.getBid(),createAd.getStatus());
    }

    /**
     * 광고 상태 변경
     * @param sessionId
     * @param dto
     * @return
     */
    @Override
    public AdvertisementApplyResponseDto applyAdvertisement(Long advertisementId, Long sessionId, AdvertisementApplyRequestDto dto) {
        // 광고 찾기
        Advertisement findAd = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ApiException(AdErrorCode.AD_NOT_FOUND));

        // 광고 상태 (APPLY / REJECT), 변경한 사람으로 값 변경
        findAd.applyAd(dto.getStatus(),dto.getRejectReason(),sessionId);
        Advertisement applyAd = advertisementRepository.save(findAd);

        return new AdvertisementApplyResponseDto(
                applyAd.getId(), applyAd.getBid(),applyAd.getStatus(),applyAd.getRejectReason(),applyAd.getAdminId(), applyAd.getAppliedAt());
    }

    /**
     * 광고 상태 확인
     * @param shopId
     * @param advertisementId
     * @param sessionId
     * @return
     */
    @Override
    public AdvertisementApplyResponseDto findAdvertisement(Long shopId, Long advertisementId, Long sessionId) {
        // 가게 찾기
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(()-> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));

        // 광고 찾기
        Advertisement findAd = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ApiException(AdErrorCode.AD_NOT_FOUND));

        // 요청한 사람 찾기
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        // 광고 본인이 아니거나 권한이 없을 때
        if(!findAd.getShop().getUser().getId().equals(sessionId) || findUser.getRole().equals("admin")){
            throw new ApiException(AdErrorCode.INVALID_SHOP_AD);
        }

        return new AdvertisementApplyResponseDto(
                findAd.getId(),findAd.getBid(), findAd.getStatus(), findAd.getRejectReason(),findAd.getAdminId(),findAd.getAppliedAt()
        );
    }
}
