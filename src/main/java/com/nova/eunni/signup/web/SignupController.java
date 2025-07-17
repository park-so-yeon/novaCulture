package com.nova.eunni.signup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nova.eunni.signup.dto.EmailVerificationRequest;
import com.nova.eunni.signup.dto.EmailVerificationVerifyRequest;
import com.nova.eunni.signup.dto.PhoneVerificationRequest;
import com.nova.eunni.signup.dto.PhoneVerificationVerifyRequest;
import com.nova.eunni.signup.dto.SignupRequest;
import com.nova.eunni.signup.service.SignupService;

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
    @PostMapping("")
    public boolean signup(@RequestBody SignupRequest request) {
        return signupService.signup(request);
    }
} 