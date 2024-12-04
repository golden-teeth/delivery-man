package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUNT(HttpStatus.NOT_FOUND,"주문을 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
