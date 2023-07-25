package com.springboot.systems.system_wide.models.core.emailtemplate.annotations;

import com.springboot.systems.system_wide.models.core.emailtemplate.EmailTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EmailTemplateInterpreter {
	
	public static final List<EmailTemplate> reflectivelyGetEmailTemplates() {

		List<EmailTemplate> emailTemplates = new ArrayList<>();

		for (Field field : EmailTemplateConstants.class.getFields()) {

			if (field.isAnnotationPresent(EmailTemplateAnnotation.class)) {
				EmailTemplateAnnotation emailTemplateAnnotation = field.getAnnotation(EmailTemplateAnnotation.class);
				EmailTemplate emailTemplate = new EmailTemplate();
				emailTemplate.setTemplateType(emailTemplateAnnotation.templateType());
				emailTemplate.setTemplate(emailTemplateAnnotation.template());
				emailTemplates.add(emailTemplate);
			}
		}
		return emailTemplates;
	}
	
}
