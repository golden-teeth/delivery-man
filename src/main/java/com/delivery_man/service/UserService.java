package com.delivery_man.service;

import com.delivery_man.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto);

    Authentication login(UserLoginRequestDto userLoginRequestDto);

    void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId);
}
