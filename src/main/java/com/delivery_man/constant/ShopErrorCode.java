package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ShopErrorCode implements ErrorCode{
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다"),

    INVALID_SHOP_AD(HttpStatus.UNAUTHORIZED, "본인의 가게에만 광고 신청을 할 수 있습니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
