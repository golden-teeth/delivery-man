package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.model.dto.user.UserLoginRequestDto;
import com.delivery_man.model.dto.auth.Authentication;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 로그인 메서드
     *
     * @param requestDto 로그인 시 요청 정보
     * @param request
     * @return 로그인 한 유저 정보
     */
    @PostMapping("/login")
    public ResponseEntity<Authentication> login(@RequestBody UserLoginRequestDto requestDto, HttpServletRequest request) {

        //로그인에 성공한 유저 정보로 객체 생성
        Authentication authentication = userService.login(requestDto);

        //session 생성
        HttpSession session = request.getSession();

        //session 값 저장
        session.setAttribute(Const.SESSION_KEY, authentication);

        return ResponseEntity.ok(authentication);
    }

    /**
     * 로그아웃 메서드
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().body("정상적으로 로그아웃 되었습니다.");
    }
}
