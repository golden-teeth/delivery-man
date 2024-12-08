package com.delivery_man.validate;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.UserErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateWithSession(Long userId, Long sessionId) {
        if(userId!= sessionId){
            throw new ApiException(UserErrorCode.INVALID_SESSION_ID);
        }
    }
}
