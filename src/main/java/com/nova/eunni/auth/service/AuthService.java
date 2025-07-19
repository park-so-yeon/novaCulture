package com.nova.eunni.auth.service;

import com.nova.eunni.auth.dto.LoginRequest;
import com.nova.eunni.auth.dto.LoginResponse;
import com.nova.eunni.auth.entity.User;
import com.nova.eunni.auth.exception.LoginException;
import com.nova.eunni.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 일반 사용자 로그인
     */
    public LoginResponse login(LoginRequest request) {
        return authenticate(request, false);
    }

    /**
     * 관리자 로그인
     */
    public LoginResponse adminLogin(LoginRequest request) {
        return authenticate(request, true);
    }

    /**
     * 공통 인증 로직
     *
     * @param request       로그인 요청 DTO
     * @param requireAdmin  true이면 ADMIN 역할 검증 포함
     * @return 로그인 응답 DTO
     */
    private LoginResponse authenticate(LoginRequest request, boolean requireAdmin) {
        User user = findUserOrThrow(request.getUserId());

        verifyPassword(request.getPassword(), user.getPassword());

        if (requireAdmin && !hasRole(user, "ADMIN")) {
            throw new LoginException("관리자가 아닙니다.", HttpStatus.FORBIDDEN);
        }

        return buildResponse(user);
    }

    /** 사용자 조회 */
    private User findUserOrThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new LoginException(
                        "아이디가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }

    /** 비밀번호 검증 */
    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new LoginException(
                    "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    /** 역할 보유 여부 확인 */
    private boolean hasRole(User user, String role) {
        return Optional.ofNullable(user.getRoles())
                .map(arr -> Arrays.asList(arr).contains(role))
                .orElse(false);
    }

    /** 응답 DTO 생성 */
    private LoginResponse buildResponse(User user) {
        return new LoginResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}
