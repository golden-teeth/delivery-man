package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.UserLoginRequestDto;
import com.delivery_man.dto.Authentication;
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

    @PostMapping("/login")
    public ResponseEntity<Authentication> login(@RequestBody UserLoginRequestDto requestDto, HttpServletRequest request) {

        Authentication authentication = userService.login(requestDto);

        HttpSession session = request.getSession();

        session.setAttribute(Const.SESSION_KEY, authentication);

        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().body("정상적으로 로그아웃 되었습니다.");
    }
}
