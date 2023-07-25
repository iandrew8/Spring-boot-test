package com.springboot.systems.system_wide.services.core.security.repo;

import com.springboot.systems.system_wide.models.core.security.Permission;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
	Optional<Permission> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
