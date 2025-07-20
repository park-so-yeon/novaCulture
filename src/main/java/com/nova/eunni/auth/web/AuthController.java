package com.nova.eunni.auth.web;

import com.nova.eunni.auth.dto.LoginRequest;
import com.nova.eunni.auth.dto.LoginResponse;
import com.nova.eunni.auth.service.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletRequest servletRequest
    ) {
        // 1) 인증 로직 수행
        LoginResponse response = authService.login(request);

        // 2) 세션 생성 및 타임아웃 설정 (예: 30분)
        HttpSession session = servletRequest.getSession(true);
        session.setAttribute("LOGIN_USER", response);
        session.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.ok(response); // 200 OK
    }
    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        LoginResponse response = authService.adminLogin(request);

        HttpSession session = servletRequest.getSession(true);
        session.setAttribute("LOGIN_USER", response);
        session.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.ok(response); // 200 OK
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 기존 세션만 가져옴
        if (session != null) {
            session.invalidate();                        // 세션 제거
        }
        return ResponseEntity.noContent().build();       // 204 No Content
    }
}
