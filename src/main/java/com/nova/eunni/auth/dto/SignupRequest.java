package com.nova.eunni.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupRequest {
    private String userId;
    private String password;
    private String phone;
    private String birth;
    private String email;
    private String address;
    private String userName;
    private LocalDateTime createdAt;
    private String isAdAgreed;

    @Override
    public String toString() {
        return "SignupRequest{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
} 