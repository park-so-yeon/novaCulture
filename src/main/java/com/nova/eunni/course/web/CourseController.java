package com.nova.eunni.course.web;

import com.nova.eunni.course.dto.CourseRequest;
import com.nova.eunni.course.dto.CourseResponse;
import com.nova.eunni.course.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * 모든 강좌 목록을 조회합니다.
     * GET /api/courses
     */
    @PostMapping("/courseList")
    public ResponseEntity<List<CourseResponse>> getAllCourses(HttpServletRequest httpRequest) {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    /**
     * 강좌 기본 정보 저장
     */
    @PostMapping(value = "/saveCourseBasic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CourseResponse> createCourse(
            HttpServletRequest httpRequest,
            @RequestPart("request") CourseRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        CourseResponse response = courseService.createCourse(request, file, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
