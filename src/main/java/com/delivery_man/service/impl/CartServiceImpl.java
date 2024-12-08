package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.CartErrorCode;
import com.delivery_man.constant.errorcode.MenuErrorCode;
import com.delivery_man.constant.errorcode.ShopErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.cart.CartCreateRequestDto;
import com.delivery_man.model.dto.cart.CartResponseDto;
import com.delivery_man.model.dto.user.UserValidDto;
import com.delivery_man.model.entity.*;
import com.delivery_man.repository.CartRepository;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.ShopRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.CartService;
import com.delivery_man.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    private final UserValidator userValidation;

    @Override
    public CartResponseDto create(CartCreateRequestDto dto) {
        //검증
        userValidation.validateWithSession(dto.getUserId(), dto.getAuthUserId());
        //user 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        //shop 검증
        Shop shop = shopRepository.findById(dto.getShopId())
                .orElseThrow(() -> new ApiException(ShopErrorCode.SHOP_NOT_FOUND));
        //menu 검증
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));

        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        //cartList 검증
        validateCartList(cartList, shop);


        /*
        요청한 메뉴가 없을 경우 장바구니에 추가
        요청한 메뉴가 있을 경우 장바구니 수정
         */
        Optional<Cart> cartByMenuId = cartList.stream()
                .filter(x -> x.getMenu().getId().equals(dto.getMenuId()))
                .findAny();

        if (cartByMenuId.isPresent()) {
            cartByMenuId.get().updateByDto(dto);
        } else {
            Cart cart = new Cart(menu, user);
            cart.updateByDto(dto);
            cartRepository.save(cart);

            cartList.add(cart);
        }


        return new CartResponseDto(cartList);
    }

    @Override
    public CartResponseDto find(Long userId, Long sessionId) {
        //검증
        userValidation.validateWithSession(userId, sessionId);
        //user 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        List<Cart> cartList = cartRepository.findByUserId(userId);
        if (cartList.isEmpty()) {
            throw new ApiException(CartErrorCode.CART_NOT_FOUND);
        }

        validateExpired(cartList);

        return new CartResponseDto(cartList);
    }


    @Override
    public CartResponseDto deleteByMenuId(UserValidDto userValidDto, Long menuId) {
        //검증
        userValidation.validateWithSession(userValidDto.getSessionId(), userValidDto.getUserId());
        //user 검증
        User user = userRepository.findById(userValidDto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        if(cartList.isEmpty()) {
            throw new ApiException(CartErrorCode.CART_NOT_FOUND);
        }

        //장바구니 메뉴가 없을 경우 error return
        Cart cart = cartList.stream()
                .filter(x -> x.getMenu().getId().equals(menuId))
                .findAny()
                .orElseThrow(() -> new ApiException(CartErrorCode.CART_NOT_FOUND));


        cartRepository.delete(cart);
        cartList.remove(cart);

        return new CartResponseDto(cartList);
    }

    /**
     * cartList를 검증
     * 1. cartList가 만료 되었는지
     * 2. cartList와 요청온 메뉴의 식당이 동일한지
     * @param cartList
     * @param shop
     */
    private void validateCartList(List<Cart> cartList, Shop shop) {
        validateExpired(cartList);
        validateShop(cartList, shop);
    }

    private static void validateShop(List<Cart> cartList, Shop shop) {
        if(!cartList.isEmpty() && !cartList.get(0).getMenu().getShop().getId().equals(shop.getId())) {
            cartList.clear();
        }
    }

    private void validateExpired(List<Cart> cartList) {

        if (isBefore1DaysAgo(cartList)) {
            cartList.clear();
        }
    }

    private boolean isBefore1DaysAgo(List<Cart> cartList) {
        return cartList.stream()
                .map(CreateAndUpdateDateEntity::getUpdatedAt)
                .sorted(Comparator.reverseOrder())
                .limit(1)
                .noneMatch(x -> x.isAfter(LocalDateTime.now().minusDays(1)));
    }


}
