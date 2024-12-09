package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.ShopStatus;
import com.delivery_man.constant.errorcode.ShopErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.menu.MenuResponseDto;
import com.delivery_man.model.dto.review.ReviewResponseDto;
import com.delivery_man.model.dto.shop.*;
import com.delivery_man.model.entity.*;
import com.delivery_man.repository.*;
import com.delivery_man.service.PictureService;
import com.delivery_man.service.S3Service;
import com.delivery_man.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    private final S3Service s3Service;
    private final PictureService pictureService;

    /**
     * 가게 생성
     *
     * @param dto
     * @param sessionId
     * @return
     */
    @Override
    public ShopWithPictureResponseDto createShop(ShopCreateRequestDto dto, Long sessionId, MultipartFile image) throws IOException {
        // 유저 확인
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        List<Shop> findShops = shopRepository.findAllByUserId(sessionId);

        // 소유 가게가 3개인지 확인
        int cnt = 0;
        for (Shop findShop : findShops) {
            if (findShop.getStatus().equals(ShopStatus.OPEN)) {
                cnt++;
            }
        }
        // 3개 이상 요청되었을 경우 에러 처리
        if (cnt >= 3) {
            throw new ApiException(ShopErrorCode.SHOP_MAXIMUM_OWN);
        }

        // 가게 생성
        Shop shop = new Shop(dto.getName(), dto.getMinimumPrice(), dto.getStatus(), dto.getOpenAt(), dto.getCloseAt(), dto.getClosedDays(), findUser);
        Shop createShop = shopRepository.save(shop);

        //picture 테이블에 담을 변수 생성
        String category = createShop.getClass().getSimpleName();
        Long idNumber = createShop.getId();
        //업로드 된 이미지 파일 주소
        String publicUrl = s3Service.uploadImage(image);

        //picture 테이블에 저장
        pictureService.savePicture(publicUrl, category, idNumber);

        return new ShopWithPictureResponseDto(createShop, publicUrl);
    }

    /**
     * 가게 목록 조회
     *
     * @param shopName
     * @return
     */
    @Override
    public List<ShopResponseDto> findAllShops(String shopName, Long sessionId) {
        // 유저 확인
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        List<Shop> findShops = new ArrayList<>();
        if ("owner".equals(findUser.getRole())) {
            findShops = shopRepository.findAllByName(shopName);
        } else if ("user".equals(findUser.getRole())) { // 유저일 경우 OPEN 인 가게만 확인 가능, 광고 입찰가대로 정렬
            findShops = shopRepository.findAllByNameAndStatus(shopName, ShopStatus.OPEN);
        }

        // 가게가 없을 때
        if (findShops.isEmpty()) {
            throw new ApiException(ShopErrorCode.SHOP_NOT_FOUND);
        }
        return findShops.stream()
                .map(ShopResponseDto::new)
                .collect(Collectors.toList());

    }

    /**
     * 가게 상세 조회
     *
     * @param shopId
     * @return
     */
    @Override
    public ShopFindOneResponseDto findShop(String sortType, int page, int size, int ratingMin, int ratingMax, Long shopId, Long sessionId) {
        // 유저 확인
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Optional<Shop> findShop = Optional.empty();
        if ("owner".equals(findUser.getRole())) {
            findShop = shopRepository.findById(shopId);
        } else if ("user".equals(findUser.getRole())) { // 유저일 경우 OPEN 상태인 가게만 확인 가능
            findShop = shopRepository.findShopByIdAndStatus(shopId, ShopStatus.OPEN);
        }

        // 가게가 없을 때
        if (findShop.isEmpty()) {
            throw new ApiException(ShopErrorCode.SHOP_NOT_FOUND);
        }

        // 가게에 등록된 메뉴 조회
        List<Menu> allMenus = menuRepository.findByShop(findShop.get());

        // 조회한 메뉴를 목록으로 저장
        List<MenuResponseDto> menusDtos = allMenus.stream().map(MenuResponseDto::new).toList();

        // 가게와 연관된 주문 조회
        List<Order> allOrders = orderRepository.findByShopId(findShop.get().getId());
        List<Long> ordersIds = allOrders.stream()
                .map(Order::getId)
                .toList();

        // 가게와 연관된 리뷰 조회
        List<Review> allReviews = reviewRepository.findReviews(ordersIds,sessionId,ratingMin,ratingMax);

        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : allReviews) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review.getId(), review.getUser().getName(), review.getContent(), review.getRating());
            reviewDtos.add(reviewResponseDto);
        }

        ShopResponseDto shopResponseDto = new ShopResponseDto(findShop.get());
        return new ShopFindOneResponseDto(
                shopResponseDto,
                menusDtos,
                reviewDtos
        );
    }

    /**
     * 가게 상태 업데이트
     *
     * @param shopId
     * @return
     */
    @Override
    @Transactional
    public ShopUpdateStatusResponseDto updateShopStatus(ShopUpdateStatusRequestDto dto, Long shopId) {
        // 가게 찾기
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));

        // 가게 상태 업데이트
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
