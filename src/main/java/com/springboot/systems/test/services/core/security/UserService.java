package com.springboot.systems.test.services.core.security;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.security.Role;

public interface UserService extends GenericService<User> {

    /**
     * Returns the logged-in user
     *
     * @return {@link User}
     */
    User getLoggedInUser();

    /**
     * This method is meant to check if the currently logged-in user is attempting to update their own profile.
     * If not, then the user is not allowed to update the profile.
     * @param userId, ID of the user to check
     * @return boolean
     */
    boolean shouldUserUpdate(String userId);

    /**
     * Returns the {@link User} with the supplied username
     * @param username, username of the user to return
     * @return {@link User}
     */
    User getUserByUsername(String username);

    /**
     * Returns the {@link User} to whom the supplied {@link Role} ID has been assigned
     * @param userId, ID of the user to assign the role to
     * @param roleId, ID of the role to assign to the user
     * @return {@link User}
     * @throws ContentNotFoundException, if the user or role is not found
     * @throws OperationFailedException, if the operation fails
     * @throws ValidationFailedException, if the validation fails
     */
    User assignRoleToUser(String userId, String roleId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException;

    /**
     * Returns the {@link User} to whom the supplied {@link Role} ID has been unassigned
     * @param userId, ID of the user to unassign the role from
     * @param roleId, ID of the role to unassign from the user
     * @return {@link User}
     * @throws ContentNotFoundException, if the user or role is not found
     * @throws OperationFailedException, if the operation fails
     * @throws ValidationFailedException, if the validation fails
     */
    User unassignRoleFromUser(String userId, String roleId) throws ContentNotFoundException, OperationFailedException, ValidationFailedException;

    /**
     * This method will create {@link User} record(s) if none exists in the database
     */
    void createDefaultUser();
}
