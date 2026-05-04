package com.nova.eunni.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role_permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 역할에 속하는가 (관리자, 선생님 등)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleMaster role;

    // 어떤 상세 권한을 가지는가 (버튼 키 등)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    // --- 수정 내역 추적 필드 ---

    @Column(name = "upd_id")
    private String updId; // 이 권한을 할당/수정한 관리자 ID [cite: 177, 179]

    @UpdateTimestamp
    @Column(name = "upd_dt")
    private LocalDateTime updDt; // 수정 시각 자동 기록 [cite: 186]
}