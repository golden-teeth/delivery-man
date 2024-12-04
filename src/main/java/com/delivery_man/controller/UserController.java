package com.delivery_man.controller;

import com.delivery_man.dto.UserSignUpRequestDto;
import com.delivery_man.dto.UserSignUpResponseDto;
import com.delivery_man.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> creatUser(
            @RequestBody UserSignUpRequestDto userSignUpRequestDto
    ) {
        return ResponseEntity.ok().body(userService.signUpUser(userSignUpRequestDto));
    }
}
