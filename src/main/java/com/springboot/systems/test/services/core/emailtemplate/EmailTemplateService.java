package com.springboot.systems.test.services.core.emailtemplate;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.models.enums.TemplateType;
import com.springboot.systems.test.models.core.emailtemplate.EmailTemplate;

public interface EmailTemplateService extends GenericService<EmailTemplate> {

    /**
     * Returns the {@link EmailTemplate} attached to the supplied {@link TemplateType}
     * @param templateType, the {@link TemplateType} to which the {@link EmailTemplate} is attached
     * @return {@link EmailTemplate}
     */
    EmailTemplate getEmailTemplateByTemplateType(TemplateType templateType);

    /**
     * This method will create a default {@link EmailTemplate} record(s) if none exists in the database
     */
    void createDefaultEmailTemplate();

}
