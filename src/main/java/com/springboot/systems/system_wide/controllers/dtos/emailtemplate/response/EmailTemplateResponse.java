package com.springboot.systems.system_wide.controllers.dtos.emailtemplate.response;

import com.springboot.systems.system_wide.controllers.dtos.BaseResponse;
import com.springboot.systems.system_wide.models.core.emailtemplate.EmailTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailTemplateResponse extends BaseResponse {
    private String template, templateType;

    public EmailTemplateResponse(EmailTemplate emailTemplate) {
        this.setId(emailTemplate.getId());
        this.setDateCreated(emailTemplate.getDateCreated());
        this.setDateChanged(emailTemplate.getDateChanged());
        this.setRecordStatus(emailTemplate.getRecordStatus());
        this.template = emailTemplate.getTemplate();
        this.templateType = emailTemplate.getTemplateType().getName();
    }
}
