package com.springboot.systems.test.services.core.security.repo;

import com.springboot.systems.test.models.core.security.Role;
import com.springboot.systems.test.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
