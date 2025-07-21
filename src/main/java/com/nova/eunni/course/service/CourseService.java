package com.nova.eunni.course.service;

import com.nova.eunni.auth.dto.LoginRequest;
import com.nova.eunni.course.dto.CourseRequest;
import com.nova.eunni.course.dto.CourseResponse;
import com.nova.eunni.course.entity.Course;
import com.nova.eunni.course.repository.CourseRepository;
import com.nova.eunni.upload.entity.FileUpload;
import com.nova.eunni.upload.repository.FileUploadRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
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

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(c -> new CourseResponse(c.getId(), c.getCourseName(), c.getDescription(), c.getImageUrl(), c.getRegID(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * 강좌 기본 정보 저장
     */
    public CourseResponse createCourse(CourseRequest request, MultipartFile file, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        String loginUserId = null;

        if (session != null && session.getAttribute("LOGIN_USER") != null) {
            Object loginUser = session.getAttribute("LOGIN_USER");

            // 로그인 객체가 String(id) 형태면
            if (loginUser instanceof String) {
                loginUserId = (String) loginUser;
            }

            // 로그인 객체가 DTO(UserDto 등)라면
            else if (loginUser instanceof LoginRequest) {
                loginUserId = ((LoginRequest) loginUser).getUserId();
            }
            // 기타 필요 시 instanceof로 타입 검사
        }

        String imageUrl = null;

        // 1. 파일이 직접 업로드된 경우
        if (file != null && !file.isEmpty()) {
            // 파일 저장 처리 (예: 로컬 저장, S3 업로드 등)
            // 예시: 업로드 후 URL 반환
            imageUrl = file.getOriginalFilename(); // 여기 실제 구현에 맞게 저장 로직 대체 필요
        }

        // 2. imageUploadId로 기존 업로드한 이미지 참조
        else if (request.getImageUploadId() != null) {
            FileUpload upload = uploadRepository.findById(request.getImageUploadId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid upload ID"));
            imageUrl = upload.getUrl();
        }

        // 3. Course 객체 생성 및 저장
        Course course = Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .regID(loginUserId)
                .build();

        Course saved = courseRepository.save(course);

        // 4. 응답 객체 반환
        return new CourseResponse(
                saved.getId(),
                saved.getCourseName(),
                saved.getDescription(),
                saved.getImageUrl(),
                saved.getRegID(),
                saved.getCreatedAt()
        );
    }

}