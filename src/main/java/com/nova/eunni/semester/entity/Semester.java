package com.nova.eunni.semester.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "semesters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {
    @Id
    private String id;

    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;

    // 등록자 및 등록일
    private String regBy;
    private LocalDateTime regDt;

    // 수정자 및 수정일
    private String updBy;
    private LocalDateTime updDt;
}