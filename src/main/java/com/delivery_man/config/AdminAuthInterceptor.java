package com.delivery_man.config;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.Const;
import com.delivery_man.constant.errorcode.SessionErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.dto.auth.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {
    /**
     * admin 권한 검증
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //session 값 확인
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ApiException(SessionErrorCode.NO_SESSION);
        }

        //session 으로 객체 생성
        Authentication authentication = (Authentication) session.getAttribute(Const.SESSION_KEY);

        //객체에서 유형 조회
        String grade = authentication.getGrade();

        //admin 인지 검증
        if (!Objects.equals(grade, "admin")) {
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }
        return true;
    }
}
