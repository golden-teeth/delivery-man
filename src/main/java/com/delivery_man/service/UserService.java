package com.delivery_man.service;

import com.delivery_man.dto.UserSignUpRequestDto;
import com.delivery_man.dto.UserSignUpResponseDto;

public interface UserService {
    UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto);
}
