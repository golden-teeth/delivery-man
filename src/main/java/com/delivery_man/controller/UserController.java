package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.Authentication;
import com.delivery_man.dto.UserLeaveRequestDto;
import com.delivery_man.dto.UserSignUpRequestDto;
import com.delivery_man.dto.UserSignUpResponseDto;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{userId}")
    public ResponseEntity<String> leaveUser(@PathVariable Long userId,
                          @RequestBody UserLeaveRequestDto userLeaveRequestDto,
                          HttpServletRequest request,
                          @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ) {
        Long sessionId = authentication.getId();
        userService.leaveUser(userId,userLeaveRequestDto,request,sessionId);

        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }
}
