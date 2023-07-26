package com.springboot.systems.test.services.core.security.impl;

import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.controllers.dtos.security.payload.UserPayload;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.core.security.annotations.roles.RoleConstants;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.Role;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.services.core.security.repo.UserRepository;
import com.springboot.systems.test.services.core.security.UserService;
import com.springboot.systems.test.services.utils.TelephoneNumberUtils;
import com.springboot.systems.test.services.utils.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getLoggedInUser() {
        return getUserByUsername(iAuthenticationFacade.getAuthentication().getName());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsernameAndRecordStatus(username, RecordStatus.ACTIVE).orElse(null);
    }

    @Override
    public User assignRoleToUser(String userId, String roleId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException {
        Role role = roleService.getInstanceByID(roleId);

        User user = getInstanceByID(userId);

        user.addRole(role);
        return saveInstance(user);
    }

    @Override
    public User unassignRoleFromUser(String userId, String roleId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException {
        Role role = roleService.getInstanceByID(roleId);

        User user = getInstanceByID(userId);

        user.removeRole(role);
        return saveInstance(user);
    }

    @Override
    public User getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return userRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE)
                .orElseThrow(() -> new ContentNotFoundException("User with ID " + instance + " was not found"));
    }

    @Override
    public User saveInstance(User entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        User user = getInstanceByID(id);
        user.setUsername("DELETED_" + user.getUsername());
        deleteInstance(user);

//        Delete this user's userProfile too
        userProfileService.deleteRecord(userProfileService.getUserProfileByUser(user).getId());
    }

    @Override
    public boolean shouldUserUpdate(String userId) {
        User user = getLoggedInUser();
        return user.getId().equals(userId);
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_USER))
            return true;

        if (actions.equals(Actions.VIEW) && !loggedInUser.getRoles().isEmpty())
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_USER))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_USER))
            return true;

        if (actions.equals(Actions.UPDATE) && !loggedInUser.hasRole(RoleConstants.ROLE_ADMIN))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_USER))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> User addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        UserPayload userPayload = (UserPayload) payload;
        User user = getUserByUsername(userPayload.getUsername());

        if (user != null)
            throw new ValidationFailedException("Username is already taken");

        Validate.emailValidator(userPayload.getEmail());

        if (userPayload.getRoleIds() == null || userPayload.getRoleIds().isEmpty())
            throw new ValidationFailedException("User must have at least one role");

        user = new User();
        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());
        user.setEmail(userPayload.getEmail());
        user.setUsername(userPayload.getUsername());
        user.setPhoneNumber(TelephoneNumberUtils.getValidTelephoneNumber(userPayload.getPhoneNumber()));
        user.setPassword(passwordEncoder.encode(userPayload.getPassword()));

        Set<Role> roles = new HashSet<>();

        for (String roleId : userPayload.getRoleIds()) {
            Role role = roleService.getInstanceByID(roleId);
            if (role != null)
                roles.add(role);
        }

        user.setRoles(roles);

        User savedUser = saveInstance(user);
        userProfileService.addNewRecord(savedUser);

        return savedUser;
    }

    @Override
    public <R> User updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        User userOfId = getInstanceByID(id);
        UserPayload userPayload = (UserPayload) payload;

        if (userPayload.getFirstName() != null)
            userOfId.setFirstName(userPayload.getFirstName());
        if (userPayload.getLastName() != null)
            userOfId.setLastName(userPayload.getLastName());
        if (userPayload.getPhoneNumber() != null)
            userOfId.setPhoneNumber(TelephoneNumberUtils.getValidTelephoneNumber(userPayload.getPhoneNumber()));
        if (userPayload.getEmail() != null) {
            Validate.emailValidator(userPayload.getEmail());
            userOfId.setEmail(userPayload.getEmail());
        }
        if (userPayload.getUsername() != null) {
            User userOfUsername = getUserByUsername(userPayload.getUsername());
            if (userOfUsername != null && !userOfUsername.getId().equals(userOfId.getId()))
                throw new ValidationFailedException("Username is already taken");
            userOfId.setUsername(userPayload.getUsername());
        }
        if (userPayload.getPassword() != null)
            userOfId.setPassword(passwordEncoder.encode(userPayload.getPassword()));
        if (userPayload.getRoleIds() != null && !userPayload.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>();

            for (String roleId : userPayload.getRoleIds()) {
                Role role = roleService.getInstanceByID(roleId);
                if (role != null)
                    roles.add(role);
            }

            userOfId.setRoles(roles);
        }

        return saveInstance(userOfId);
    }

    @Override
    public boolean isDeletable(User instance) throws OperationFailedException {
        return true;
    }

    @Override
    public void createDefaultUser() {
        try {
            if (getUserByUsername("administrator") == null) {
                User user = new User();
                user.setFirstName("Administrator");
                user.setLastName("System");
                user.setUsername("administrator");
                user.setEmail("admin@bcs.com");
                user.setPhoneNumber("0787689679");
                user.setPassword(passwordEncoder.encode("password123"));
                Role role = roleService.getRoleByName(RoleConstants.ROLE_ADMIN);
                user.addRole(role);

                User savedUser = saveInstance(user);
                userProfileService.addNewRecord(savedUser);
            }
        } catch (Exception e) {
            log.error("User Seeder Error: {}", e.getMessage());
        }
    }
}
