package com.nova.eunni.signup.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import com.nova.eunni.signup.dto.SignupRequest; // 이 DTO가 필요하다면 유지

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SignupService {

    private final JavaMailSender mailSender;
    private final Map<String, String> emailCodeMap = new HashMap<>();
    private final Map<String, String> phoneCodeMap = new HashMap<>();
    private final Random random = new Random();

    public SignupService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 이메일 인증번호 발송
    public String sendEmailVerificationCode(String email) {
        String code = String.format("%06d", random.nextInt(1000000));
        emailCodeMap.put(email, code);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // setFrom은 application.properties의 spring.mail.username과 동일하게 설정됩니다.
            // 명시적으로 지정해도 되고, 생략해도 됩니다.
            // message.setFrom("your_gmail_id@gmail.com"); // 필요하다면 본인 Gmail 주소 기입
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

        System.out.println("[이메일 인증] " + email + " -> " + code);
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
        // 실제로는 SMS 발송 로직 필요 (Twilio, Solapi 등 SMS API 연동)
        System.out.println("[휴대폰 인증] " + phone + " -> " + code);
        return code;
    }

    // 휴대폰 인증번호 확인
    public boolean verifyPhoneCode(String phone, String code) {
        String storedCode = phoneCodeMap.remove(phone);
        return code != null && code.equals(storedCode);
    }

    // 회원가입 (stub)
    public boolean signup(SignupRequest request) {
        System.out.println("[회원가입] 사용자 ID: " + request.getUserId() + ", 이메일: " + request.getEmail() + ", 휴대폰: " + request.getPhone());
        return true;
    }
}