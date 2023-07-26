package com.springboot.systems.test.services.core.security.impl;

import com.springboot.systems.test.models.core.security.annotations.roles.RoleConstants;
import com.springboot.systems.test.models.core.security.annotations.roles.RoleInterpreter;
import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.controllers.dtos.security.payload.RolePayload;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.Permission;
import com.springboot.systems.test.models.core.security.Role;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.services.core.security.repo.RoleRepository;
import com.springboot.systems.test.services.core.security.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        return super.searchUniqueByPropertyEqual("name", roleName);
    }

    @Override
    public Role assignPermission(String roleId, String permissionId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException {
        Role role = getInstanceByID(roleId);

        Permission permission = permissionService.getInstanceByID(permissionId);

        role.addPermission(permission);
        return saveInstance(role);
    }

    @Override
    public Role unassignPermission(String roleId, String permissionId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException {
        Role role = getInstanceByID(roleId);

        Permission permission = permissionService.getInstanceByID(permissionId);

        role.removePermission(permission);
        return saveInstance(role);
    }

    @Override
    public Role saveInstance(Role entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.save(entityInstance);
    }

    @Override
    public Role getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return roleRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Role of ID " + instance + " was not found"));
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_ROLE))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_ROLE))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_ROLE))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_ROLE))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> Role addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        RolePayload rolePayload = (RolePayload) payload;
        Role role = getRoleByName(rolePayload.getName());

        if (role != null)
            throw new OperationFailedException("Role with name " + rolePayload.getName() + " already exists");

        role = new Role();
        role.setName(rolePayload.getName());
        role.setDescription(rolePayload.getDescription());

        Set<Permission> permissions = new HashSet<>();

        for (String permissionId : rolePayload.getPermissionIds()) {
            Permission permission = permissionService.getInstanceByID(permissionId);
            if (permission != null)
                permissions.add(permission);
        }

        role.setPermissions(permissions);
        return saveInstance(role);
    }

    @Override
    public <R> Role updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        RolePayload rolePayload = (RolePayload) payload;
        Role role = getInstanceByID(id);

        if (rolePayload.getName() != null && StringUtils.isNotBlank(rolePayload.getName()))
            role.setName(rolePayload.getName());
        if (rolePayload.getDescription() != null && StringUtils.isNotBlank(rolePayload.getDescription()))
            role.setDescription(rolePayload.getDescription());

        if (rolePayload.getPermissionIds() != null && !rolePayload.getPermissionIds().isEmpty()) {
            Set<Permission> permissions = new HashSet<>();
            for (String permissionId : rolePayload.getPermissionIds()) {
                Permission permission = permissionService.getInstanceByID(permissionId);
                if (permission != null)
                    permissions.add(permission);
            }
            role.setPermissions(permissions);
        }

        return saveInstance(role);
    }

    @Override
    public boolean isDeletable(Role instance) throws OperationFailedException {
        return true;
    }

    @Override
    public void createDefaultRole() {
        RoleInterpreter.reflectivelyGetRoles().forEach(role -> {
            if (getRoleByName(role.getName()) == null) {
                try {
                    if (role.getName().equalsIgnoreCase(RoleConstants.ROLE_ADMIN))
                        role.setPermissions(new HashSet<>(permissionService.getAllInstances()));
                    else
                        role.addPermission(permissionService.getPermissionByName(PermissionConstants.PERM_USE_SYSTEM));

                    /*-----Handle ISP Admin permissions-----*/
                    if (role.getName().equalsIgnoreCase(RoleConstants.ROLE_ISP_ADMIN)) {
                        role.addPermission(permissionService.getPermissionByName(PermissionConstants.PERM_VIEW_ISP_CLIENT));
                        role.addPermission(permissionService.getPermissionByName(PermissionConstants.PERM_UPDATE_CLIENT_EXPIRY));
                        role.addPermission(permissionService.getPermissionByName(PermissionConstants.PERM_VIEW_ACCESS_KEY));
                        role.addPermission(permissionService.getPermissionByName(PermissionConstants.PERM_ADD_ACCESS_KEY));
                    }
                    saveInstance(role);
                } catch (Exception e) {
                    log.error("Role Seeder Error: {}", e.getMessage());
                }
            }
        });
    }
}
