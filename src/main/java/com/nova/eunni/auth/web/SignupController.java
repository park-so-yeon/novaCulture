package com.nova.eunni.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nova.eunni.auth.dto.EmailVerificationRequest;
import com.nova.eunni.auth.dto.EmailVerificationVerifyRequest;
import com.nova.eunni.auth.dto.PhoneVerificationRequest;
import com.nova.eunni.auth.dto.PhoneVerificationVerifyRequest;
import com.nova.eunni.auth.dto.SignupRequest;
import com.nova.eunni.auth.service.SignupService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/signup")
public class SignupController {
    private final SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    // 이메일 인증번호 발송
    @PostMapping("/send-email-code")
    public String sendEmailCode(@RequestBody EmailVerificationRequest request) {
        return signupService.sendEmailVerificationCode(request.getEmail());
    }

    // 이메일 인증번호 확인
    @PostMapping("/verify-email-code")
    public boolean verifyEmailCode(@RequestBody EmailVerificationVerifyRequest request) {
        return signupService.verifyEmailCode(request.getEmail(), request.getCode());
    }

    // 휴대폰 인증번호 발송
    @PostMapping("/send-phone-code")
    public String sendPhoneCode(@RequestBody PhoneVerificationRequest request) {
        return signupService.sendPhoneVerificationCode(request.getPhone());
    }

    // 휴대폰 인증번호 확인
    @PostMapping("/verify-phone-code")
    public boolean verifyPhoneCode(@RequestBody PhoneVerificationVerifyRequest request) {
        return signupService.verifyPhoneCode(request.getPhone(), request.getCode());
    }

    // 회원가입
    @PostMapping("/userRegister")
    public boolean signup(@RequestBody SignupRequest request) {
        return signupService.signup(request);
    }

    // 아이디 중복 체크 API
    @GetMapping("/check-id")
    public Map<String, Boolean> checkDuplicateId(@RequestParam("userId") String userId) {
        boolean available = signupService.isUserIdAvailable(userId);
        return Collections.singletonMap("available", available);
    }
}
