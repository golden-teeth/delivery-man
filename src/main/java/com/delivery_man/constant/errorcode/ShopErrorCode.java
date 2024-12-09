package com.delivery_man.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ShopErrorCode implements ErrorCode {
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다"),

    SHOP_MAXIMUM_OWN(HttpStatus.BAD_REQUEST,"더 이상 생성할 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
