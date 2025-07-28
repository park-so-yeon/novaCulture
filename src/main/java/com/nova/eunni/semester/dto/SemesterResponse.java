package com.nova.eunni.semester.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 학기 정보 응답용 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterResponse {
    private String id;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;

    private String regBy;
    private LocalDateTime regDt;

    private String updBy;
    private LocalDateTime updDt;
}
