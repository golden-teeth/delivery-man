package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}