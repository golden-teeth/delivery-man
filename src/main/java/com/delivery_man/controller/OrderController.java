package com.delivery_man.controller;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.config.Const;
import com.delivery_man.constant.OrderErrorCode;
import com.delivery_man.dto.Authentication;
import com.delivery_man.dto.OrderCreateRequestDto;
import com.delivery_man.dto.OrderResponseDto;
import com.delivery_man.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@PathVariable Long userId,
                                                   @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication,
                                                   @Valid @RequestBody OrderCreateRequestDto dto){
        if(userId!= authentication.getId()){
            throw new ApiException(OrderErrorCode.ORDER_UNAUTHORIZED);
        }
        dto.updateIds(authentication.getId());
        OrderResponseDto responseDto = orderService.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
