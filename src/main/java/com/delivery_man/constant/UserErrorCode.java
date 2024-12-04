package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    /* 401 권한 없음 */
    INVALID_GRADE(HttpStatus.NOT_FOUND, "권한이 없습니다."),

    /* 404 찾을수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}
