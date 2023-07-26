package com.springboot.systems.test.services.core.setup.impl;

import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.controllers.dtos.setup.payload.ApplicationSettingPayload;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.setup.ApplicationSetting;
import com.springboot.systems.test.services.core.setup.repo.ApplicationSettingRepository;
import com.springboot.systems.test.services.core.setup.ApplicationSettingService;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicationSettingServiceImpl extends GenericServiceImpl<ApplicationSetting> implements ApplicationSettingService {

    private final ApplicationSettingRepository applicationSettingRepository;

    @Override
    public ApplicationSetting saveInstance(ApplicationSetting entityInstance) throws ValidationFailedException, OperationFailedException {
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(ApplicationSetting instance) throws OperationFailedException {
        return true;
    }

    @Override
    public ApplicationSetting getActiveApplicationSetting() {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return super.searchUnique(search);
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_APPLICATION_SETTING))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_APPLICATION_SETTING))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_APPLICATION_SETTING))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_APPLICATION_SETTING))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> ApplicationSetting addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        /*-----Check if a record exists, return it if it does and create a new one if it doesn't-----*/
        ApplicationSetting applicationSetting = getActiveApplicationSetting();
        ApplicationSettingPayload applicationSettingPayload = (ApplicationSettingPayload) payload;

        if (applicationSetting == null)
            applicationSetting = new ApplicationSetting();

        applicationSetting.setSystemEmail(applicationSettingPayload.getSystemEmail());
        applicationSetting.setSystemEmailHost(applicationSettingPayload.getSystemEmailHost());
        applicationSetting.setSystemEmailPassword(applicationSettingPayload.getSystemEmailPassword());
        applicationSetting.setSystemEmailPort(applicationSettingPayload.getSystemEmailPort());
        applicationSetting.setSystemEmailSender(applicationSettingPayload.getSystemEmailSender());

        return saveInstance(applicationSetting);
    }

    @Override
    public <R> ApplicationSetting updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        ApplicationSetting applicationSettingToEdit = getInstanceByID(id);
        ApplicationSettingPayload applicationSettingPayload = (ApplicationSettingPayload) payload;

        if (applicationSettingPayload.getSystemEmail() != null)
            applicationSettingToEdit.setSystemEmail(applicationSettingPayload.getSystemEmail());

        if (applicationSettingPayload.getSystemEmailHost() != null)
            applicationSettingToEdit.setSystemEmailHost(applicationSettingPayload.getSystemEmailHost());

        if (applicationSettingPayload.getSystemEmailPassword() != null)
            applicationSettingToEdit.setSystemEmailPassword(applicationSettingPayload.getSystemEmailPassword());

        if (applicationSettingPayload.getSystemEmailPort() != null)
            applicationSettingToEdit.setSystemEmailPort(applicationSettingPayload.getSystemEmailPort());

        if (applicationSettingPayload.getSystemEmailSender() != null)
            applicationSettingToEdit.setSystemEmailSender(applicationSettingPayload.getSystemEmailSender());

        return saveInstance(applicationSettingToEdit);
    }

    @Override
    public ApplicationSetting getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return applicationSettingRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Application Setting of Id: " + instance + " was not found")
        );
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public void createDefaultApplicationSetting() {
        try {
            if (applicationSettingService.getAllInstances().isEmpty()) {
                ApplicationSetting applicationSetting = new ApplicationSetting();
                applicationSetting.setSystemEmail("ibandaandrew8@gmail.com");
                applicationSetting.setSystemEmailSender("ibandaandrew8@gmail.com");
                applicationSetting.setSystemEmailHost("smtp.gmail.com");
                applicationSetting.setSystemEmailPort("587");
                applicationSetting.setSystemEmailPassword("yyhmjgdpjiocmryu");
                applicationSettingService.saveInstance(applicationSetting);
            }
        } catch (ValidationFailedException | OperationFailedException e) {
            log.error("Application Setting Seeder Error: {}", e.getMessage());
        }
    }
}
