package com.springboot.systems.system_wide.controllers.dtos.emailtemplate.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTemplatePayload {
    private String template, templateType;
}
