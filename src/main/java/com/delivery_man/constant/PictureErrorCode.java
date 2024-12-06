package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PictureErrorCode implements ErrorCode {

    /* 400 잘못된 요청 */
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "지원하는 파일 형식이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
