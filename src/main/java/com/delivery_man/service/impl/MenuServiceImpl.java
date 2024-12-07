package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.MenuErrorCode;
import com.delivery_man.constant.ShopErrorCode;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.menu.MenuCreateRequestDto;
import com.delivery_man.dto.menu.MenuResponseDto;
import com.delivery_man.dto.menu.MenuUpdateRequestDto;
import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.MenuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public MenuResponseDto create(MenuCreateRequestDto dto) {
        //검증
        //shop id 검증
        Shop shop = shopRepository.findById(dto.getShopId())
                        .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //user id 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Menu menu = new Menu(dto);
        menu.updateShop(shop);
        Menu savedMenu = menuRepository.save(menu);
        return new MenuResponseDto(savedMenu);
    }

    @Override
    public MenuResponseDto update(MenuUpdateRequestDto dto) {
        //검증
        //dto 검증
        validateUpdateDto(dto);
        //shop id 검증
        Shop shop = shopRepository.findById(dto.getShopId()).
                orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //user id 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        //menu 검증
        Menu findByMenuId = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));


        findByMenuId.update(dto);

        return new MenuResponseDto(findByMenuId);

    }

    @Override
    public void delete(Long shopId, Long menuId, Long userId) {
        //검증
        //user id 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        //shop 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));

        //menuId 검증
        Menu menuById = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException());


        menuById.delete();
    }


    private void validateUpdateDto(MenuUpdateRequestDto dto) {
        if ((dto.getName() == null && dto.getPrice() != null) ||
                (dto.getName() != null && dto.getPrice() == null)) {
            throw new RuntimeException();
        }
    }

}
