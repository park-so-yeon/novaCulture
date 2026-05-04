package com.nova.eunni.upload.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // MongoDB의 @Document 대신 JPA 엔티티로 변경
@Table(name = "file_uploads")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL auto_increment
    private Long id; // 관계형 DB 관례에 따른 Long 타입

    @Column(nullable = false)
    private String fileName;

    private String fileType;

    @Column(columnDefinition = "TEXT") // URL이 길어질 수 있으므로 TEXT 타입 권장
    private String url;

    private long size;

    @CreationTimestamp // 등록일시 자동 기록
    @Column(updatable = false)
    private LocalDateTime regDt;

    private String regId; // 등록자 ID

    @UpdateTimestamp // 수정일시 자동 기록
    private LocalDateTime updDt;

    private String updId; // 수정자 ID (관리자 페이지 추적용)
}