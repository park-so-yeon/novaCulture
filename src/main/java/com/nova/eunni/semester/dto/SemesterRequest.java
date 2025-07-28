package com.nova.eunni.semester.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 학기 등록 요청용 DTO
 * 날짜는 문자열(yyyy-MM-dd) 형식으로 받는다
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterRequest {
    private String semesterName;   // 예: "2025 여름학기"
    private String startDate;      // 예: "2025-06-01"
    private String endDate;        // 예: "2025-08-31"
    private String regBy;          // 등록자 (ex. "admin")
}
