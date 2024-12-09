package com.delivery_man.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ORDER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"본인의 주문 사항만 볼 수 있습니다"),

    ORDER_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "최소 주문 금액 이상 주문이 가능합니다"),

    /* 404 찾을 수 없음 */
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}
