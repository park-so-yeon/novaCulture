package com.nova.eunni.semester.web;

import com.nova.eunni.semester.entity.Semester;
import com.nova.eunni.semester.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping("/semesterList")
    public ResponseEntity<List<Semester>> getSemesterList() {
        List<Semester> semesters = semesterService.getAllSemesters();
        return ResponseEntity.ok(semesters);
    }
}
