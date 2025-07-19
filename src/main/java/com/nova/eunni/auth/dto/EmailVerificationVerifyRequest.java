package com.nova.eunni.auth.dto;

public class EmailVerificationVerifyRequest {
    private String email;
    private String code;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
} 