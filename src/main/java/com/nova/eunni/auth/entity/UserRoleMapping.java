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
@Table(name = "user_roles") // DB의 매핑 테이블 이름과 일치
public class UserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. 사용자 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 2. 역할(Role) 연결
    @ManyToOne(fetch = FetchType.EAGER) // 권한은 보통 바로 같이 사용하므로 EAGER 권장
    @JoinColumn(name = "role_id")
    private RoleMaster role;

    // --- 수정 내역 추적 필드 ---

    @Column(name = "upd_id")
    private String updId; // 권한을 부여하거나 변경한 관리자 ID

    @UpdateTimestamp
    @Column(name = "upd_dt")
    private LocalDateTime updDt; // 수정 시각 자동 기록
}