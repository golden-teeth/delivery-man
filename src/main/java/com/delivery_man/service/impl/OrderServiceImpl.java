package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.MenuErrorCode;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.OrderCreateRequestDto;
import com.delivery_man.dto.OrderResponseDto;
import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Order;
import com.delivery_man.entity.User;
import com.delivery_man.repository.MenuRepository;
import com.delivery_man.repository.OrderRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    @Override
    public OrderResponseDto create(OrderCreateRequestDto dto) {
        //검증
        //user 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        // menu 검증
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.MENU_NOT_FOUND));

        Order order = new Order(dto);
        order.updateMenu(menu);
        order.updateUser(user);

        orderRepository.save(order);
        return new OrderResponseDto(order);

    }
}
