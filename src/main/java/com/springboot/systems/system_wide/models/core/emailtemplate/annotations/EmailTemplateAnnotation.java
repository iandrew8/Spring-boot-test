package com.springboot.systems.system_wide.models.core.emailtemplate.annotations;

import com.springboot.systems.system_wide.models.enums.TemplateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailTemplateAnnotation {
	TemplateType templateType();

	String template();
}
