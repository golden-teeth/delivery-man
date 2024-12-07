package com.delivery_man.service;

import com.delivery_man.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto, MultipartFile image) throws IOException;

    Authentication login(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto findUser(Long userId, Long sessionId);

    void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId);
}
