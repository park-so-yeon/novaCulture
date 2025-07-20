package com.nova.eunni.course.web;

import com.nova.eunni.course.dto.CourseRequest;
import com.nova.eunni.course.dto.CourseResponse;
import com.nova.eunni.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        System.out.println(courses.toString());
        return ResponseEntity.ok(courses);
    }

    /**
     * 강좌 기본 정보 저장
     */
    @PostMapping("/saveCourseBasic")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
