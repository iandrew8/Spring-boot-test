package com.springboot.systems.system_wide.services.core.logs.repo;

import com.springboot.systems.system_wide.models.core.logs.Log;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    Optional<Log> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
