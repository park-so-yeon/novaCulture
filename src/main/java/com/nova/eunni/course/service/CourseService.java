package com.nova.eunni.course.service;

import com.nova.eunni.course.dto.CourseRequest;
import com.nova.eunni.course.dto.CourseResponse;
import com.nova.eunni.course.entity.Course;
import com.nova.eunni.course.repository.CourseRepository;
import com.nova.eunni.upload.entity.FileUpload;
import com.nova.eunni.upload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final FileUploadRepository uploadRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, FileUploadRepository uploadRepository) {
        this.courseRepository = courseRepository;
        this.uploadRepository = uploadRepository;
    }

    /**
     * 강좌 기본 정보 저장
     */
    public CourseResponse createCourse(CourseRequest request) {
        String imageUrl = null;
        if (request.getImageUploadId() != null) {
            FileUpload upload = uploadRepository.findById(request.getImageUploadId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid upload ID"));
            imageUrl = upload.getUrl();
        }
        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Course saved = courseRepository.save(course);
        return new CourseResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getImageUrl()
        );
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(c -> new CourseResponse(c.getId(), c.getName(), c.getDescription(), c.getImageUrl()))
                .collect(Collectors.toList());
    }
}