package com.nova.eunni.course.repository;

import com.nova.eunni.course.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    // 기본 CRUD 외에 필요시 커스텀 메서드를 추가하세요
    List<Course> findByNameContaining(String keyword);
}
