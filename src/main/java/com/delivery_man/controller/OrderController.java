package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.model.dto.order.OrderResponseDto;
import com.delivery_man.model.dto.order.OrderUpdateRequestDto;
import com.delivery_man.model.dto.user.UserValidDto;
import com.delivery_man.service.OrderService;
import com.delivery_man.service.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final PointService pointService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<OrderResponseDto> create(@PathVariable Long userId,
                                                   @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication) {

        UserValidDto userValidDto = new UserValidDto(userId, authentication.getId());
        OrderResponseDto responseDto = orderService.create(userValidDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/shops/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> update(@PathVariable Long orderId,
                                                   @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication,
                                                   @Valid @RequestBody OrderUpdateRequestDto dto) {
        dto.updateIds(orderId, authentication.getId());
        OrderResponseDto responseDto = orderService.update(dto);

        //포인트 서비스 호출
        pointService.createPoint(orderId, responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
