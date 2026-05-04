package com.nova.eunni.auth.repository;

import com.nova.eunni.auth.entity.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {
    Optional<RoleMaster> findByRoleName(String roleName);
}