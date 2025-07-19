package com.nova.eunni.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "courseDetail")
class CourseSchedule {
    private String id;        // 스케줄 고유 ID (UUID 등)
    private String dayOfWeek;  // 요일 (MON, TUE 등)
    private int period;        // 교시 (1~n)
    private int minAge;        // 최소 연령
    private int maxAge;        // 최대 연령
}
