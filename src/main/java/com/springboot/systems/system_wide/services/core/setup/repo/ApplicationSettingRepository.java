package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.core.setup.ApplicationSetting;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting, String> {
    Optional<ApplicationSetting> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
