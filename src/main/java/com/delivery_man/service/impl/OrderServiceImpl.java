package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.CartErrorCode;
import com.delivery_man.constant.errorcode.OrderErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.order.OrderResponseDto;
import com.delivery_man.model.dto.order.OrderUpdateRequestDto;
import com.delivery_man.model.dto.user.UserValidDto;
import com.delivery_man.model.entity.Cart;
import com.delivery_man.model.entity.Order;
import com.delivery_man.model.entity.OrderItem;
import com.delivery_man.model.entity.User;
import com.delivery_man.repository.CartRepository;
import com.delivery_man.repository.OrderItemRepository;
import com.delivery_man.repository.OrderRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.OrderService;
import com.delivery_man.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    private final UserValidator userValidator;
    @Override
    public OrderResponseDto create(UserValidDto dto) {
        //검증
        // user session 검증
        userValidator.validateWithSession(dto.getUserId(),dto.getSessionId());
        //user 검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        // cart 검증
        List<Cart> carts = cartRepository.findByUserId(user.getId());
        if(carts.isEmpty()){
            throw new ApiException(CartErrorCode.CART_NOT_FOUND);
        }

        // 장바구니를 주문으로 변경
        Order order = new Order(carts);

        //주문 저장
        orderRepository.save(order);

        //주문 메뉴에 주문정보 업데이트
        addOrderToOrderItems(order);
        
        //주문 메뉴 저장
        orderItemRepository.saveAll(order.getOrderItems());

        //주문 후 장바구니 비우기
        cartRepository.deleteAll(carts);

        return new OrderResponseDto(order);
    }

    private void addOrderToOrderItems(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }
    }


    @Override
    public OrderResponseDto update(OrderUpdateRequestDto dto) {
        //검증
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if (!user.getRole().equals("owner")) {
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ApiException(OrderErrorCode.ORDER_NOT_FOUND));

        order.updateStatus(dto.getStatus());

        return new OrderResponseDto(order);
    }
}
