package com.delivery_man.service.Impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.config.PasswordEncoder;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.*;
import com.delivery_man.entity.User;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto) {

        Optional<User> findUser = userRepository.findByEmail(userSignUpRequestDto.getEmail());

        if (findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        return new UserSignUpResponseDto(savedUser);
    }

    @Override
    public Authentication login(UserLoginRequestDto userLoginRequestDto) {
        String email = userLoginRequestDto.getEmail();

        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return new Authentication(findUser.get().getId(),findUser.get().getEmail(), findUser.get().getGrade());
    }

    @Transactional
    @Override
    public void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId) {

        if (!userId.equals(sessionId)) {
            throw new ApiException(UserErrorCode.INVALID_SESSION_ID);
        }

        User findUser = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if (!PasswordEncoder.matches(userLeaveRequestDto.getPassword(), findUser.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }

        findUser.updateStatus("leave");

        userRepository.save(findUser);
    }
}
