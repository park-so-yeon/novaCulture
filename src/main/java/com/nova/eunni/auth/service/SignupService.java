package com.nova.eunni.auth.service;

import com.nova.eunni.auth.dto.SignupRequest;
import com.nova.eunni.auth.entity.RoleMaster;
import com.nova.eunni.auth.entity.User;
import com.nova.eunni.auth.entity.UserRoleMapping;
import com.nova.eunni.auth.repository.RoleMasterRepository;
import com.nova.eunni.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final JavaMailSender mailSender;
    private final Map<String, String> emailCodeMap = new HashMap<>();
    private final Random random = new Random();

    @Transactional
    public boolean signup(SignupRequest request) {
        if(userRepository.existsByUserId(request.getUserId())) {
            return false;
        }

        // 1. 유저 엔티티 빌드
        User user = User.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .isVerified(true)
                .isAdAgreed(request.isAdAgreed())
                .regId("SYSTEM")
                .updId("SYSTEM")
                .build();

        // 2. 기본 권한(ROLE_USER) 조회 [cite: 249]
        RoleMaster userRole = roleMasterRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("기본 권한 'ROLE_USER'가 DB에 없습니다. SQL을 먼저 실행하세요."));

        // 3. 매핑 테이블에 저장 [cite: 247]
        UserRoleMapping mapping = new UserRoleMapping();
        mapping.setUser(user);
        mapping.setRole(userRole);
        mapping.setUpdId("SYSTEM");

        user.getUserRoles().add(mapping);

        userRepository.save(user);
        return true;
    }

    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsByUserId(userId);
    }

    public String sendEmailVerificationCode(String email) {
        String code = String.format("%06d", random.nextInt(1000000));
        emailCodeMap.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[노바문화센터] 인증번호");
        message.setText("인증번호: " + code);
        mailSender.send(message);

        return code;
    }

    public boolean verifyEmailCode(String email, String code) {
        return code != null && code.equals(emailCodeMap.remove(email));
    }

    public String sendPhoneVerificationCode(String phone) {
        // 실제 SMS 연동이 필요하지만 지금은 콘솔에 찍거나 Mocking 처리
        String code = String.format("%06d", random.nextInt(1000000));
        // phoneCodeMap.put(phone, code);
        return code;
    }

    public boolean verifyPhoneCode(String phone, String code) {
        // 실제 검증 로직 추가
        return true;
    }
}
