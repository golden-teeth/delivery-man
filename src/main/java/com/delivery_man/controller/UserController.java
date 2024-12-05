package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.*;
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

    /**
     * 회원 가입 메서드
     *
     * @param userSignUpRequestDto 회원 가입 시 요청 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> creatUser(
            @RequestBody UserSignUpRequestDto userSignUpRequestDto
    ) {
        return ResponseEntity.ok().body(userService.signUpUser(userSignUpRequestDto));
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
                                                    HttpServletRequest request,
                                                    @SessionAttribute(name = Const.SESSION_KEY) Authentication session) {

        Long sessionId = session.getId();
        return ResponseEntity.ok().body(userService.findUser(userId, sessionId));
    }
}
