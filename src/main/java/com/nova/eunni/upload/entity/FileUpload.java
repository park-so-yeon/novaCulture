package com.nova.eunni.upload.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "file_uploads")
public class FileUpload {
    @Id
    private String id;
    private String fileName;
    private String fileType;
    private String url;
    private long size;
    private LocalDateTime createdAt;
}
