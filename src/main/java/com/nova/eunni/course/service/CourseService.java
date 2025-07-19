package com.nova.eunni.course.service;

import com.nova.eunni.course.dto.CourseRequest;
import com.nova.eunni.course.dto.CourseResponse;
import com.nova.eunni.course.entity.Course;
import com.nova.eunni.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseResponse(course.getId(), course.getName(), course.getDescription()))
                .collect(Collectors.toList());
    }

    public CourseResponse getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        return new CourseResponse(course.getId(), course.getName(), course.getDescription());
    }

    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        Course saved = courseRepository.save(course);
        return new CourseResponse(saved.getId(), saved.getName(), saved.getDescription());
    }

    public CourseResponse updateCourse(String id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        Course updated = courseRepository.save(course);
        return new CourseResponse(updated.getId(), updated.getName(), updated.getDescription());
    }

    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        courseRepository.deleteById(id);
    }
}