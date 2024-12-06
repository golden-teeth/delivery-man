package com.delivery_man.service;

import com.delivery_man.dto.*;
import com.delivery_man.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    User signUpUser(UserSignUpRequestDto userSignUpRequestDto);

    Authentication login(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto findUser(Long userId, Long sessionId);

    void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId);
}
