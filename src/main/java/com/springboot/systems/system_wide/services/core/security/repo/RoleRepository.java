package com.springboot.systems.system_wide.services.core.security.repo;

import com.springboot.systems.system_wide.models.core.security.Role;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
