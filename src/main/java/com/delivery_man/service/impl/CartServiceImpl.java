package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.MenuErrorCode;
import com.delivery_man.constant.ShopErrorCode;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.CartCreateRequestDto;
import com.delivery_man.dto.CartResponseDto;
import com.delivery_man.entity.Cart;
import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.repository.CartRepository;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    @Override
    public CartResponseDto create(CartCreateRequestDto dto) {
        //검증
        validateSessionAndUser(dto);
        //user 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        //shop 검증
        Shop shop = shopRepository.findById(dto.getShopId())
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //menu 검증
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));

        Optional<Cart> byUserIdAndMenuId = cartRepository.findByUserIdAndMenuId(user.getId(), menu.getId());
        Cart cart = new Cart();
        if (byUserIdAndMenuId.isEmpty()) {
            cart.updateMenu(menu);
            cart.updateUser(user);
            cartRepository.save(cart);
        }else {
            cart = byUserIdAndMenuId.get();
        }


        cart.updateByDto(dto);


        CartResponseDto cartResponseDto = new CartResponseDto(cart);
        return cartResponseDto;
    }

    private static void validateSessionAndUser(CartCreateRequestDto dto) {
        if (dto.getAuthUserId() != dto.getUserId()) {
            throw new ApiException(UserErrorCode.INVALID_SESSION_ID);
        }
    }
}
