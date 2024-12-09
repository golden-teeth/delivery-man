package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.model.dto.user.UserLeaveRequestDto;
import com.delivery_man.model.dto.user.UserResponseDto;
import com.delivery_man.model.dto.user.UserSignUpRequestDto;
import com.delivery_man.model.dto.user.UserSignUpResponseDto;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 메서드
     *
     * @param userSignUpRequestDto 회원 가입 시 요청 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> creatUser(
            @RequestPart("request") UserSignUpRequestDto userSignUpRequestDto,
            @RequestPart(value = "images", required = false) MultipartFile image
            ) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUpUser(userSignUpRequestDto, image));
    }

    /**
     * 회원 탈퇴 메서드
     *
     * @param userId              탈퇴 할 유저 식별자
     * @param userLeaveRequestDto 탈퇴 시 요청 정보
     * @param request
     * @param authentication      session 정보
     * @return
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<String> leaveUser(@PathVariable Long userId,
                                            @RequestBody UserLeaveRequestDto userLeaveRequestDto,
                                            HttpServletRequest request,
                                            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ) {
        Long sessionId = authentication.getId();
        userService.leaveUser(userId, userLeaveRequestDto, request, sessionId);

        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }

    /**
     * 회원 조회 메서드
     *
     * @param userId 유저 식별자
     * @return 회원 정보와 포인트
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId,
                                                    @SessionAttribute(name = Const.SESSION_KEY) Authentication session) {

        Long sessionId = session.getId();
        return ResponseEntity.ok().body(userService.findUser(userId, sessionId));
    }
}
