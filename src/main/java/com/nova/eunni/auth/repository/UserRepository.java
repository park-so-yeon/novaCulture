package com.nova.eunni.auth.repository;

import com.nova.eunni.auth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);
}