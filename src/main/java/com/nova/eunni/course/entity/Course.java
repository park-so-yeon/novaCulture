package com.nova.eunni.course.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 강좌 엔티티
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    /** 강좌명 */
    private String courseName;

    /** 강좌 설명 */
    private String description;

    private String imageUrl;

    /** 생성일 */
    private LocalDateTime createdAt;

    /** 수정일 */
    private LocalDateTime updatedAt;

    private String regID;

    private String updId;
    /**
     * 교시별 스케줄 목록
     * CourseSchedule 클래스는 별도 정의 필요
     */
    @Builder.Default
    private List<CourseSchedule> schedules = new ArrayList<>();
}