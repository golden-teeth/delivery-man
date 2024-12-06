package com.delivery_man.config;

import com.delivery_man.filter.LoginFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String[] OWNER_AUTH_REQUIRED_PATH_PATTERNS = {"/shops", "/shops/*","/advertisement/*"};
    private static final String[] USER_AUTH_REQUIRED_PATH_PATTERNS = {"/users/*/orders","/users/*/orders/*","/orders/*/reviews"};

    private final UserAuthInterceptor userAuthInterceptor;
    private final OwnerAuthInterceptor ownerAuthInterceptor;

    /**
     * 로그인 필터 등록
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    /**
     * 인터셈터 등록
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor)
                .addPathPatterns(USER_AUTH_REQUIRED_PATH_PATTERNS)
                .order(2);

        registry.addInterceptor(ownerAuthInterceptor)
                .addPathPatterns(OWNER_AUTH_REQUIRED_PATH_PATTERNS)
                .order(3);
    }
}
