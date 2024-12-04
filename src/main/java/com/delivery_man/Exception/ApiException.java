package com.delivery_man.Exception;

import com.delivery_man.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
