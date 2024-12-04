package com.delivery_man.service.impl;

import com.delivery_man.dto.MenuResponseDto;
import com.delivery_man.dto.ShopCreateRequestDto;
import com.delivery_man.dto.ShopFindOneResponseDto;
import com.delivery_man.dto.ShopResponseDto;
import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.constant.ShopStatus;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

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
    public ShopFindOneResponseDto findShop(Long shopId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 가게에 등록된 메뉴 조회
        List<Menu> allMenus = menuRepository.findByShop(findShop);

        // 조회한 메뉴를 목록으로 저장
        List<MenuResponseDto> menus = allMenus.stream().map(MenuResponseDto::new).toList();

        ShopResponseDto shopResponseDto = new ShopResponseDto(findShop);
        return new ShopFindOneResponseDto(
                shopResponseDto,
                menus
        );
    }
}
