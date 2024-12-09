package com.delivery_man.filter;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.Exception.ErrorResponse;
import com.delivery_man.constant.Const;
import com.delivery_man.constant.errorcode.SessionErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {

    //로그인 필터 화이트 리스트
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            if (!isWhiteList(requestURI)) {
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(Const.SESSION_KEY) == null) {
                    throw new ApiException(SessionErrorCode.NO_SESSION);
                }
            }
            chain.doFilter(request, response);
        }catch (ApiException e){
            httpResponse.setStatus(e.getErrorCode().getHttpStatus().value());
            httpResponse.setContentType("application/json;charset=UTF-8");

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(e.getErrorCode().name())
                    .message(e.getErrorCode().getMessage())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            httpResponse.getWriter().write(jsonResponse);
        }

    }

    //화이트 리스트 검증 메서드
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
