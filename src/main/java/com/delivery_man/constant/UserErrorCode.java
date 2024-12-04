package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    INVALID_SESSION_ID(HttpStatus.UNAUTHORIZED,"사용자 정보와 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀 번호가 일치하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
