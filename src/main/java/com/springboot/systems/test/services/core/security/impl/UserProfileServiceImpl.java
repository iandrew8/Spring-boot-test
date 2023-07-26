package com.springboot.systems.test.services.core.security.impl;

import com.springboot.systems.test.models.core.emailtemplate.EmailTemplate;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.security.UserProfile;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.core.security.annotations.roles.RoleConstants;
import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.enums.TemplateType;
import com.springboot.systems.test.services.core.security.UserProfileService;
import com.springboot.systems.test.services.core.security.repo.UserProfileRepository;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl extends GenericServiceImpl<UserProfile> implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return userProfileRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("User profile with ID: " + instance + " was not found")
        );
    }

    @Override
    public UserProfile getUserProfileByUser(User user) {
        return super.searchUniqueByPropertyEqual("user", user, RecordStatus.ACTIVE);
    }

    @Override
    public void sendUserCreationEmail(UserProfile userProfile) throws Exception {
        String subject = "Account Creation";

        /*-----Get Email Template-----*/
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateByTemplateType(TemplateType.USER_ACCOUNT_CREATION);
        String emailBody;

        if (emailTemplate != null) {
            emailBody = emailTemplate.getTemplate();
            emailBody = emailBody.replace("{user}", userProfile.getUser().getFullName());
            emailBody = emailBody.replace("{username}", userProfile.getUser().getUsername());
            emailBody = emailBody.replace("{password}", "password123");
        } else {
            emailBody = "Hello "+ userProfile.getUser().getFullName() +",<br />  " +
                    "An account has been created for you on the Billing system. Here are you credentials;<br />" +
                    "Username: "+ userProfile.getUser().getUsername() +" <br />" +
                    "Temporary Password: password123 <br />" +
                    "Contact Admin to get access. Please change your password on your first login.";
        }

        /*-----Send the email-----*/
        emailClientService.sendMail(userProfile.getUser().getEmail(), subject, emailBody);

        /*-----Log this action-----*/
        log.info("Account creation email sent to " + userProfile.getUser().getFullName());
        logService.createLog("Account creation email sent to " + userProfile.getUser().getFullName());
    }

    @Override
    public UserProfile saveInstance(UserProfile entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_USER_PROFILE))
            return true;

        if (actions.equals(Actions.VIEW) && loggedInUser.hasRole(RoleConstants.ROLE_ISP_ADMIN))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_USER_PROFILE))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_USER_PROFILE))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_USER_PROFILE))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> UserProfile addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        User user = (User) payload;
        UserProfile userProfile = getUserProfileByUser(user);

        if (userProfile != null)
            throw new ValidationFailedException("User profile already exists for user: " + user.getUsername());

        userProfile = new UserProfile();
        userProfile.setUser(user);
        UserProfile savedProfile = saveInstance(userProfile);

        /*-----Send Account Creation Email-----*/
        try {
            sendUserCreationEmail(savedProfile);
        } catch (Exception e) {
            log.error("UserProfileServiceImpl {}", e.getMessage());
        }

        return savedProfile;
    }

    @Override
    public <R> UserProfile updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        User user = (User) payload;
        UserProfile userProfileToEdit = getInstanceByID(id);

        if (user != null)
            userProfileToEdit.setUser(user);

        return saveInstance(userProfileToEdit);
    }

    @Override
    public boolean isDeletable(UserProfile instance) throws OperationFailedException {
        return true;
    }
}
