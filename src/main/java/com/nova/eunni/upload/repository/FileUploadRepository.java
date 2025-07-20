package com.nova.eunni.upload.repository;

import com.nova.eunni.upload.entity.FileUpload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends MongoRepository<FileUpload, String> {
}
