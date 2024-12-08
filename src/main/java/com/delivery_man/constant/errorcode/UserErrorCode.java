package com.delivery_man.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    /* 400 잘못된 입력값 */
    INVALID_INPUT_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),

    /* 401 권한 없음 */
    INVALID_GRADE(HttpStatus.NOT_FOUND, "권한이 없습니다."),
    INVALID_SESSION_ID(HttpStatus.UNAUTHORIZED, "사용자 정보와 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다."),

    /* 404 찾을수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
