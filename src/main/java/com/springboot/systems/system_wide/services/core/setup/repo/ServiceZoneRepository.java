package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.core.setup.ServiceZone;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceZoneRepository extends JpaRepository<ServiceZone, String> {
    Optional<ServiceZone> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
