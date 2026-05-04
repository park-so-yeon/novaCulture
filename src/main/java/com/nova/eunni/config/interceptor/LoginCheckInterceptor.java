package com.nova.eunni.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        // 1. CORS를 위한 OPTIONS 요청은 무조건 통과
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 2. WebConfig에서 제외했지만, 한 번 더 확실히 경로 체크 (선택 사항)
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/signup/")) {
            return true;
        }

        // 3. 세션 체크
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("LOGIN_USER") != null);

        if (!isLoggedIn) {
            // 세션이 없으면 401 에러를 반환하며 요청을 중단시킴 [cite: 187, 188]
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}