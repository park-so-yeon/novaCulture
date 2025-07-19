package com.nova.eunni.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
