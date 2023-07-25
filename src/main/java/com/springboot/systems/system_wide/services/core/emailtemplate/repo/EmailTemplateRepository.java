package com.springboot.systems.system_wide.services.core.emailtemplate.repo;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.enums.TemplateType;
import com.springboot.systems.system_wide.models.core.emailtemplate.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {
    Optional<EmailTemplate> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
    Optional<EmailTemplate> findByTemplateTypeAndRecordStatus(TemplateType templateType, RecordStatus recordStatus);

}
