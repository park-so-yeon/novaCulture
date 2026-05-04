package com.nova.eunni.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String birth;
    private boolean isVerified;
    private boolean isAdAgreed;

    private String regId;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDt;
    private String updId;
    @UpdateTimestamp
    private LocalDateTime updDt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserRoleMapping> userRoles = new ArrayList<>();
}
