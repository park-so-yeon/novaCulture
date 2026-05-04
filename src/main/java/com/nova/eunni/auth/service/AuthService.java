package com.nova.eunni.auth.service;

import com.nova.eunni.auth.dto.LoginRequest;
import com.nova.eunni.auth.dto.LoginResponse;
import com.nova.eunni.auth.entity.User;
import com.nova.eunni.auth.exception.LoginException;
import com.nova.eunni.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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

        if (requireAdmin && !hasRole(user, "ROLE_ADMIN")) {
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
        // 암호화되지 않은 텍스트 데이터의 경우 예외 처리
        if (encodedPassword != null && !encodedPassword.startsWith("{bcrypt}") && !encodedPassword.startsWith("$2a$") && !encodedPassword.startsWith("$2b$") && !encodedPassword.startsWith("$2y$")) {
            logger.warn("Password for user is not properly encoded with BCrypt. It should start with $2a$, $2b$, or $2y$.");
            if (!rawPassword.equals(encodedPassword)) {
                throw new LoginException("비밀번호가 일치하지 않습니다. (평문 불일치)", HttpStatus.UNAUTHORIZED);
            }
            return;
        }

        try {
            // 입력받은 비밀번호(rawPassword)가 BCrypt로 해시된 비밀번호(encodedPassword)와 일치하는지 확인
            if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
                logger.warn("Password mismatch for BCrypt verification");
                throw new LoginException(
                        "비밀번호가 일치하지 않습니다. (BCrypt 불일치)", HttpStatus.UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Failed to match password using BCrypt: {}", e.getMessage());
            throw new LoginException("비밀번호 검증 중 오류가 발생했습니다. (유효하지 않은 암호화 형식)", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 역할 보유 여부 확인 */
    private boolean hasRole(User user, String roleName) {
        return user.getUserRoles().stream()
                .anyMatch(userRoleMapping -> userRoleMapping.getRole().getRoleName().equals(roleName));
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
