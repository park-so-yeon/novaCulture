package com.nova.eunni.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotBlank(message = "강좌명은 필수 입력입니다.")
    @Size(max = 100, message = "강좌명은 최대 100자까지 입력 가능합니다.")
    private String name;

    @Size(max = 500, message = "설명은 최대 500자까지 입력 가능합니다.")
    private String description;

    /** 업로드된 이미지 파일의 참조 ID */
    private String imageUploadId;
}