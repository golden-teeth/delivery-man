package com.delivery_man.service;

import com.delivery_man.dto.auth.Authentication;
import com.delivery_man.dto.user.*;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto);

    Authentication login(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto findUser(Long userId, Long sessionId);

    void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId);
}
