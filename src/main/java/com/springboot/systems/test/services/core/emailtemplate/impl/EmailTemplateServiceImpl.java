package com.springboot.systems.test.services.core.emailtemplate.impl;

import com.springboot.systems.test.controllers.dtos.emailtemplate.payload.EmailTemplatePayload;
import com.springboot.systems.test.models.core.emailtemplate.EmailTemplate;
import com.springboot.systems.test.models.core.emailtemplate.annotations.EmailTemplateInterpreter;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.enums.TemplateType;
import com.springboot.systems.test.services.core.emailtemplate.EmailTemplateService;
import com.springboot.systems.test.services.core.emailtemplate.repo.EmailTemplateRepository;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateServiceImpl extends GenericServiceImpl<EmailTemplate> implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplate saveInstance(EmailTemplate entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(EmailTemplate instance) throws OperationFailedException {
        return true;
    }

    @Override
    public EmailTemplate getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return emailTemplateRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Email Template of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public void deleteRecord(String id) throws ContentNotFoundException, OperationFailedException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_EMAIL_TEMPLATE))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_EMAIL_TEMPLATE))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_EMAIL_TEMPLATE))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_EMAIL_TEMPLATE))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> EmailTemplate addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        EmailTemplatePayload emailTemplatePayload = (EmailTemplatePayload) payload;

        if (emailTemplateRepository.findByTemplateTypeAndRecordStatus(TemplateType.getEnumObject(emailTemplatePayload
                .getTemplateType()), RecordStatus.ACTIVE).isPresent())
            throw new OperationFailedException("Email template for "+emailTemplatePayload.getTemplate()+" already exists.");

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setTemplateType(TemplateType.getEnumObject(emailTemplatePayload.getTemplateType()));
        emailTemplate.setTemplate(emailTemplatePayload.getTemplate());
        return saveInstance(emailTemplate);
    }

    @Override
    public <R> EmailTemplate updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        EmailTemplate emailTemplateToEdit = getInstanceByID(id);
        EmailTemplatePayload emailTemplatePayload = (EmailTemplatePayload) payload;

        if (emailTemplatePayload.getTemplate() != null && StringUtils.isNotBlank(emailTemplatePayload.getTemplate()))
            emailTemplateToEdit.setTemplate(emailTemplatePayload.getTemplate());
        if (emailTemplatePayload.getTemplateType() != null && StringUtils.isNotBlank(emailTemplatePayload.getTemplateType()))
            emailTemplateToEdit.setTemplateType(TemplateType.getEnumObject(emailTemplatePayload.getTemplateType()));

        return saveInstance(emailTemplateToEdit);
    }

    @Override
    public EmailTemplate getEmailTemplateByTemplateType(TemplateType templateType) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                .addFilterEqual("templateType", templateType);
        return super.searchUnique(search);
    }

    @Override
    public void createDefaultEmailTemplate() {
        EmailTemplateInterpreter.reflectivelyGetEmailTemplates().forEach(emailTemplate -> {
            if (emailTemplateRepository.findByTemplateTypeAndRecordStatus(emailTemplate.getTemplateType(),
                    RecordStatus.ACTIVE).isEmpty()) {
                try {
                    emailTemplateRepository.save(emailTemplate);
                } catch (Exception e) {
                    log.error("Email Templates Seeder Error: {}", e.getMessage());
                }
            }
        });
    }
}
