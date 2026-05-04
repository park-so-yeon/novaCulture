package com.nova.eunni.semester.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // JPA 엔티티 선언
@Table(name = "semesters") // 테이블 명칭 지정 [cite: 79]
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL auto_increment 적용 [cite: 73, 81]
    private Long id; // String에서 Long으로 변경하여 성능 최적화 [cite: 221]

    @Column(nullable = false)
    private String semesterName; // 예: "2026년도 1학기"

    private LocalDate startDate;
    private LocalDate endDate;

    // --- 등록 및 수정 내역 (관리자 페이지 추적용) --- [cite: 177, 189]

    @Column(updatable = false)
    private String regBy; // 등록자 ID

    @CreationTimestamp // 등록 시 자동 시간 기록 [cite: 64, 223]
    @Column(updatable = false)
    private LocalDateTime regDt;

    private String updBy; // 수정자 ID (최종 수정 관리자) [cite: 191]

    @UpdateTimestamp // 수정 시 자동 시간 갱신 [cite: 83, 186]
    private LocalDateTime updDt;
}