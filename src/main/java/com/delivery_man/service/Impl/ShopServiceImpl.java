package com.delivery_man.service.impl;

import com.delivery_man.dto.ShopCreateRequestDto;
import com.delivery_man.dto.ShopResponseDto;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.enums.ShopStatus;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

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
}
