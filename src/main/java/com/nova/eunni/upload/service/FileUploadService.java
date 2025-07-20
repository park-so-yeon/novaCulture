package com.nova.eunni.upload.service;

import com.nova.eunni.upload.entity.FileUpload;
import com.nova.eunni.upload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileUploadService {
    private static final String UPLOAD_DIR = "uploads";

    private final FileUploadRepository uploadRepository;

    @Autowired
    public FileUploadService(FileUploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    /**
     * MultipartFile을 저장하고 DB에 메타데이터 기록
     */
    public FileUpload storeFile(MultipartFile file) {
        try {
            // 1) 물리 파일 저장 경로 구성
            String originalName = file.getOriginalFilename();
            String extension = originalName != null && originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf('.')) : "";
            String generatedName = UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(generatedName);
            Files.copy(file.getInputStream(), filePath);

            // 2) URL 생성 (필요 시 도메인/컨텍스트 경로 조합)
            String fileUrl = "/" + UPLOAD_DIR + "/" + generatedName;

            // 3) 엔티티 빌드 및 저장
            FileUpload upload = FileUpload.builder()
                    .fileName(originalName)
                    .fileType(file.getContentType())
                    .url(fileUrl)
                    .size(file.getSize())
                    .createdAt(LocalDateTime.now())
                    .build();
            return uploadRepository.save(upload);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }
}
