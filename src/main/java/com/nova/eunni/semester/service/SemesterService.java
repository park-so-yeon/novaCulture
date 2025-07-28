package com.nova.eunni.semester.service;

import com.nova.eunni.semester.entity.Semester;
import com.nova.eunni.semester.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }
}