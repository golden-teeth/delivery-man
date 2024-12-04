package com.delivery_man.service;

import com.delivery_man.dto.UserSignUpRequestDto;
import com.delivery_man.dto.UserSignUpResponseDto;
import com.delivery_man.entity.User;
import com.delivery_man.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto) {

        Optional<User> findUser = userRepository.findByEmail(userSignUpRequestDto.getEmail());

        if (findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        return new UserSignUpResponseDto(savedUser);
    }
}
