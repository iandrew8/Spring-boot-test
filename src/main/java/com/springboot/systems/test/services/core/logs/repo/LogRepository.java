package com.springboot.systems.test.services.core.logs.repo;

import com.springboot.systems.test.models.core.logs.Log;
import com.springboot.systems.test.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    Optional<Log> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
