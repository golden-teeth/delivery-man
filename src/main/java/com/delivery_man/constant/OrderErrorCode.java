package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ORDER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"본인의 주문 사항만 볼 수 있습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}
