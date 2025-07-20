package com.nova.eunni.upload.controller;

import com.nova.eunni.upload.dto.FileUploadResponse;
import com.nova.eunni.upload.entity.FileUpload;
import com.nova.eunni.upload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    /**
     * 파일 업로드
     * POST /api/uploads
     */
    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        FileUpload upload = fileUploadService.storeFile(file);
        FileUploadResponse response = new FileUploadResponse(upload.getId(), upload.getUrl());
        return ResponseEntity.ok(response);
    }
}