package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.MenuErrorCode;
import com.delivery_man.constant.OrderErrorCode;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.order.OrderCreateRequestDto;
import com.delivery_man.dto.order.OrderResponseDto;
import com.delivery_man.dto.order.OrderUpdateRequestDto;
import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Order;
import com.delivery_man.entity.Shop;
import com.delivery_man.entity.User;
import com.delivery_man.repository.*;
import com.delivery_man.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    @Override
    public OrderResponseDto create(OrderCreateRequestDto dto) {
        //검증
        // menu 검증
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));
        //user 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Optional<Shop> shop = shopRepository.findById(menu.getShop().getId());

        Order order = new Order(dto);
        order.updateMenu(menu);
        order.updateUser(user);
        order.updateShop(shop.get());

        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    @Override
    public OrderResponseDto update(OrderUpdateRequestDto dto) {
        //검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if (!user.getGrade().equals("owner")) {
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }

        List<Shop> allByUserId = shopRepository.findAllByUserId(user.getId());
        for (Shop shop : allByUserId) {
            if(shop.getUser().getId().equals(dto.getUserId())) {
                continue;
            }
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ApiException(OrderErrorCode.ORDER_NOT_FOUND));

        order.updateStatus(dto.getStatus());

        return new OrderResponseDto(order);
    }
}
