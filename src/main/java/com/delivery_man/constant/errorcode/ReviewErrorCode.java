package com.delivery_man.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    /* 400 잘못된 요청 */
    REVIEW_NOT_CREATED(HttpStatus.BAD_REQUEST, "주문 완료 후 리뷰를 작성해주세요."),

    /* 401 */
    INVALID_AUTHOR(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
