package com.springboot.systems.system_wide.services.core.security.impl;

import com.springboot.systems.system_wide.controllers.dtos.security.payload.PermissionPayload;
import com.springboot.systems.system_wide.models.core.security.Permission;
import com.springboot.systems.system_wide.models.core.security.User;
import com.springboot.systems.system_wide.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.system_wide.models.core.security.annotations.permissions.PermissionInterpreter;
import com.springboot.systems.system_wide.models.enums.Actions;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.core.security.PermissionService;
import com.springboot.systems.system_wide.services.core.security.repo.PermissionRepository;
import com.springboot.systems.system_wide.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
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
public class PermissionServiceImpl extends GenericServiceImpl<Permission> implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission getPermissionByName(String permissionName) {
        return super.searchUniqueByPropertyEqual("name", permissionName);
    }

    @Override
    public Permission saveInstance(Permission entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.save(entityInstance);
    }

    @Override
    public Permission getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return permissionRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Permission with id " + instance + " was not found"));
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        throw new OperationFailedException("Permission cannot be deleted");
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_PERMISSION))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_PERMISSION))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_PERMISSION))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_PERMISSION))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> Permission addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException,
            OperationFailedException, ParseException {
        throw new OperationFailedException("Permission cannot be added");
    }

    @Override
    public <R> Permission updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException,
            OperationFailedException, ParseException {
        Permission permissionToEdit = getInstanceByID(id);
        PermissionPayload permissionPayload = (PermissionPayload) payload;

        if (permissionPayload.getName() != null && StringUtils.isNotBlank(permissionPayload.getName()))
            permissionToEdit.setName(permissionPayload.getName());

        if (permissionPayload.getDescription() != null && StringUtils.isNotBlank(permissionPayload.getDescription()))
            permissionToEdit.setDescription(permissionPayload.getDescription());

        return saveInstance(permissionToEdit);
    }

    @Override
    public boolean isDeletable(Permission instance) throws OperationFailedException {
        return false;
    }

    @Override
    public void createDefaultPermission() {
        PermissionInterpreter.reflectivelyGetPermissions().forEach(permission -> {
            if (getPermissionByName(permission.getName()) == null) {
                try {
                    saveInstance(permission);
                } catch (Exception e) {
                    log.error("Permission Seeder Error: {}", e.getMessage());
                }
            }
        });
    }
}
