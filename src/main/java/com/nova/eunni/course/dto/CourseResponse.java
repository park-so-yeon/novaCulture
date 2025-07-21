package com.nova.eunni.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String id;
    private String courseName;
    private String description;
    private String imageUrl;
    private String regId;
    private LocalDateTime createdAt;
}