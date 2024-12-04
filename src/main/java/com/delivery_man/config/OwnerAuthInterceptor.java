package com.delivery_man.config;

import com.delivery_man.dto.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class OwnerAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new RuntimeException("session is null");
        }

        Authentication authentication = (Authentication) session.getAttribute(Const.SESSION_KEY);

        String grade = authentication.getGrade();

        if (!Objects.equals(grade, "owner")) {
            throw new RuntimeException("grade is not owner");
        }
        return true;
    }
}
