package com.springboot.systems.system_wide.services.core.security;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.models.core.security.Role;
import com.springboot.systems.system_wide.models.core.security.Permission;

public interface RoleService extends GenericService<Role> {

    /**
     * Get a {@link Role} of name
     *
     * @param roleName, the name of the {@link Role} to be retrieved
     * @return {@link Role}
     */
    Role getRoleByName(String roleName);

    /**
     * Attach a {@link Permission} to a {@link Role}
     *
     * @param roleId, the id of the {@link Role} to be attached to
     * @param permissionId, the id of the {@link Permission} to be attached
     * @return {@link Role}
     */
    Role assignPermission(String roleId, String permissionId) throws ContentNotFoundException, OperationFailedException,
            ValidationFailedException;

    /**
     * Detach a {@link Permission} from a {@link Role}
     *
     * @param roleId, the id of the {@link Role} to be detached from
     * @param permissionId, the id of the {@link Permission} to be detached
     * @return {@link Role}
     */
    Role unassignPermission(String roleId, String permissionId) throws ContentNotFoundException, OperationFailedException,
            ValidationFailedException;

    /**
     * This method will create {@link Role} record(s) if none exists in the database
     */
    void createDefaultRole();

}
