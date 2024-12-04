package com.delivery_man.config;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class OwnerAuthInterceptor implements HandlerInterceptor {

    private static final String[] PATH_PATTERNS = {"/shops","/shops/*"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new RuntimeException("session is null");
        }

        String requestURI = request.getRequestURI();

        if(isPassPath(requestURI) && "GET".equalsIgnoreCase(request.getMethod()) ){
            return true;
        }

        Authentication authentication = (Authentication) session.getAttribute(Const.SESSION_KEY);

        String grade = authentication.getGrade();

        if (!Objects.equals(grade, "owner")) {
            throw new ApiException(UserErrorCode.INVALID_GRADE);
        }
        return true;
    }

    private boolean isPassPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(PATH_PATTERNS, requestURI);
    }
}
