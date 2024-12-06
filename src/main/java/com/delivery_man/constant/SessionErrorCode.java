package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode {
    /* 401 세션 없음 */
    NO_SESSION(HttpStatus.UNAUTHORIZED, "로그인 후 사용해주세요"),;

    private final HttpStatus httpStatus;
    private final String message;
}
