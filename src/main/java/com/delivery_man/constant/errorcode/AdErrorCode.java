package com.delivery_man.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AdErrorCode implements ErrorCode {
    /* 404 찾을 수 없음 */
    AD_NOT_FOUND(HttpStatus.NOT_FOUND, "광고 신청 건을 찾을 수 없습니다"),

    /* 401 권한 없음 */
    INVALID_SHOP_AD(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다."),;
    private final HttpStatus httpStatus;
    private final String message;
}
