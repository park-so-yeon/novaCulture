package com.nova.eunni.auth.service;

import com.nova.eunni.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import com.nova.eunni.auth.dto.SignupRequest;
import com.nova.eunni.auth.repository.UserRepository;  // UserRepository 추가

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final Map<String, String> emailCodeMap = new HashMap<>();
    private final Map<String, String> phoneCodeMap = new HashMap<>();
    private final Random random = new Random();

    @Autowired
    public SignupService(UserRepository userRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder; // ✅ 이제 제대로 주입됨
    }

    // 이메일 인증번호 발송
    public String sendEmailVerificationCode(String email) {
        String code = String.format("%06d", random.nextInt(1000000));
        emailCodeMap.put(email, code);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[노바문화센터] 이메일 인증번호 안내");
            message.setText("안녕하세요. 노바문화센터 이메일 인증번호는 다음과 같습니다:\n\n" +
                    "인증번호: " + code + "\n\n" +
                    "정확히 입력하여 본인인증을 완료해주세요.");

            mailSender.send(message);
            System.out.println("[이메일 발송 성공] " + email + " -> " + code);

        } catch (Exception e) {
            System.err.println("[이메일 발송 실패] " + email + " - 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("이메일 발송에 실패했습니다. 다시 시도해주세요.", e);
        }

        return code;
    }

    // 이메일 인증번호 확인
    public boolean verifyEmailCode(String email, String code) {
        String storedCode = emailCodeMap.remove(email);
        return code != null && code.equals(storedCode);
    }

    // 휴대폰 인증번호 발송
    public String sendPhoneVerificationCode(String phone) {
        String code = String.format("%06d", random.nextInt(1000000));
        phoneCodeMap.put(phone, code);
        System.out.println("[휴대폰 인증] " + phone + " -> " + code);
        return code;
    }

    // 휴대폰 인증번호 확인
    public boolean verifyPhoneCode(String phone, String code) {
        String storedCode = phoneCodeMap.remove(phone);
        return code != null && code.equals(storedCode);
    }

    // 회원가입 로직 MongoDB 연동하여 구현 (추가/수정)
    public boolean signup(SignupRequest request) {
        if(userRepository.existsByUserId(request.getUserId())) {
            return false;
        }
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(new String[]{"USER"});

        userRepository.save(user);
        return true;
    }

    // 아이디 중복 여부 확인
    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsByUserId(userId);
    }
}
