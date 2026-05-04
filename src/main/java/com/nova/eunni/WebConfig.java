package com.nova.eunni;

import com.nova.eunni.config.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**") // 모든 경로를 일단 감시하되
                .excludePathPatterns(
                        "/", "/index.html", "/login", "/signup",
                        "/css/**", "/js/**", "/images/**", "/favicon.ico",
                        "/api/auth/**",
                        "/api/signup/**" // 👈 이 설정이 있어야 회원가입 관련 API가 401 에러 없이 통과됩니다[cite: 184, 186].
                );
    }
}