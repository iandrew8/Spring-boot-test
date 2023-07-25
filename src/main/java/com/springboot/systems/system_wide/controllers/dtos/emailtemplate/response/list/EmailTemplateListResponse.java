package com.springboot.systems.system_wide.controllers.dtos.emailtemplate.response.list;

import com.springboot.systems.system_wide.controllers.dtos.emailtemplate.response.EmailTemplateResponse;
import com.springboot.systems.system_wide.models.core.emailtemplate.EmailTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmailTemplateListResponse {
    private List<EmailTemplateResponse> records = new ArrayList<>();

    public EmailTemplateListResponse(List<EmailTemplate> emailTemplates) {
        emailTemplates.forEach(emailTemplate ->
                this.records.add(new EmailTemplateResponse(emailTemplate)));
    }
}
