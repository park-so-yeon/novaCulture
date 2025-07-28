package com.nova.eunni.semester.repository;

import com.nova.eunni.semester.entity.Semester;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SemesterRepository extends MongoRepository<Semester, String> {
    // 추가 쿼리 필요 시 여기에 작성
}