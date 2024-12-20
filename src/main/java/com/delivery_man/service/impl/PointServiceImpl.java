package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.OrderErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.order.OrderResponseDto;
import com.delivery_man.model.entity.Order;
import com.delivery_man.model.entity.Point;
import com.delivery_man.model.entity.User;
import com.delivery_man.repository.OrderRepository;
import com.delivery_man.repository.PointRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /**
     * 포인트 생성 로직
     *
     * @param orderId           주문 식별자
     * @param orderResponseDto  상태를 변경한 주문 응답
     */
    @Transactional
    @Override
    public void createPoint(Long orderId, OrderResponseDto orderResponseDto) {

        //유저 조회
        User user = userRepository.findById(orderResponseDto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        //주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.ORDER_NOT_FOUND));

        //주문이 배달이 완료된 경우
        if (Objects.equals(order.getStatus(), "done")) {
            BigDecimal totalPoint = order.getTotalPrice().multiply(BigDecimal.valueOf(0.03));
            Point point = new Point();
            point.updatePoint(totalPoint, user);
            pointRepository.save(point);
        }
    }
}
