package com.delivery_man.config;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.SessionErrorCode;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.auth.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class OwnerAuthInterceptor implements HandlerInterceptor {

    private static final String[] PATH_PATTERNS = {"/shops", "/shops/*"};

    /**
     * owner 권한 검증
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

        String requestURI = request.getRequestURI();

        //검증 대상인지 확인
        if (isPassPath(requestURI) && "GET".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        //session 으로 객체 생성
        Authentication authentication = (Authentication) session.getAttribute(Const.SESSION_KEY);

        //객체에서 유형 조회
        String grade = authentication.getGrade();

        //owner 인지 검증
        if (!Objects.equals(grade, "owner")) {
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }
        return true;
    }

    //검증 대상 확인 로직
    private boolean isPassPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(PATH_PATTERNS, requestURI);
    }
}
