package com.springboot.systems.system_wide.models.core.emailtemplate;

import com.springboot.systems.system_wide.models.enums.TemplateType;
import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold all email templates used to when sending out emails.
 *
 * @author ttc
 */
@Entity
@Table(name = "email_templates")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class EmailTemplate extends Auditable {
    private TemplateType templateType;
    private String template;

    public void validate() throws ValidationFailedException {
        Validate.notNull(this.getTemplate(), "Template is required");
        Validate.notNull(this.getTemplateType(), "Template type is required");
    }

    @Column(name = "template_type")
    @Enumerated(EnumType.STRING)
    public TemplateType getTemplateType() {
        return templateType;
    }

    @Column(name = "template", columnDefinition = "text")
    public String getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return this.templateType.getName();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof EmailTemplate && (getId() != null) ? getId().equals(((EmailTemplate) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
