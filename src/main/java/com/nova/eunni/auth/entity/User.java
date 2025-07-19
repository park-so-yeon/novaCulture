package com.nova.eunni.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // 컬렉션 이름 지정
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String userId;
    private String email;
    private String password;
    private String userName;
    private String phone;
    private String address;
    private String birth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String[] roles;
    private boolean isVerified;
    private String isAdAgreed;
}