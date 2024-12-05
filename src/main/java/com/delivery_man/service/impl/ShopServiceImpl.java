package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.Exception.GlobalExceptionHandler;
import com.delivery_man.constant.ShopErrorCode;
import com.delivery_man.dto.*;
import com.delivery_man.entity.*;
import com.delivery_man.constant.ShopStatus;
import com.delivery_man.repository.*;
import com.delivery_man.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    /**
     * 가게 생성
     * @param dto
     * @param sessionId
     * @return
     */
    @Override
    public ShopResponseDto createShop(ShopCreateRequestDto dto, Long sessionId) {
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."));
        List<Shop> findShops = shopRepository.findAllByUserId(sessionId);
        int cnt = 0;
        for (Shop findShop : findShops) {
            if (findShop.getStatus().equals(ShopStatus.OPEN)) {
                cnt++;
            }
        }
        if(cnt >= 3){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"가게 소유는 3개만 가능합니다.");
        }

        Shop shop = new Shop(dto.getName(),dto.getMinimumPrice(),dto.getStatus(),dto.getOpenAt(), dto.getCloseAt(), dto.getClosedDays(),findUser);
        Shop createShop = shopRepository.save(shop);
        return new ShopResponseDto(createShop);
    }

    /**
     * 가게 목록 조회
     * @param shopName
     * @return
     */
    @Override
    public List<ShopResponseDto> findAllShops(String shopName) {
        List<Shop> findShops = shopRepository.findAllByName(shopName);
        if(findShops.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다");
        }
        return findShops.stream()
                .map(ShopResponseDto::new)
                .collect(Collectors.toList());

    }

    /**
     * 가게 상세 조회
     * @param shopId
     * @return
     */
    @Override
    public ShopFindOneResponseDto findShop(Long shopId, Long sessionId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 가게에 등록된 메뉴 조회
        List<Menu> allMenus = menuRepository.findByShop(findShop);

        // 조회한 메뉴를 목록으로 저장
        List<MenuResponseDto> menusDtos = allMenus.stream().map(MenuResponseDto::new).toList();

        // 가게와 연관된 리뷰 조회
        List<Order> allOrders = orderRepository.findByShopId(findShop.getId());
        List<Long> ordersIds = allOrders.stream()
                .map(Order::getId)
                .toList();
        List<Review> allReviews = reviewRepository.findByOrderIdInAndUserIdNot(ordersIds, sessionId);
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for(Review review : allReviews){
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review.getId(), review.getUser().getName(),review.getContent(),review.getRating());
            reviewDtos.add(reviewResponseDto);
        }

        ShopResponseDto shopResponseDto = new ShopResponseDto(findShop);
        return new ShopFindOneResponseDto(
                shopResponseDto,
                menusDtos,
                reviewDtos
        );
    }

    /**
     * 가게 상태 업데이트
     * @param shopId
     * @return
     */
    @Override
    @Transactional
    public ShopUpdateStatusResponseDto updateShopStatus(ShopUpdateStatusRequestDto dto,Long shopId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));

        findShop.updateStatus(dto.getStatus());
        Shop updateShop = shopRepository.save(findShop);
        return new ShopUpdateStatusResponseDto(
                updateShop.getId(),
                updateShop.getName(),
                updateShop.getStatus(),
                updateShop.getUpdatedAt()
        );
    }
}
