package com.springboot.systems.test.services.core.security;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.models.core.security.Permission;

public interface PermissionService extends GenericService<Permission> {

    /**
     * Get a {@link Permission} of name
     *
     * @param permissionName, the name of the {@link Permission} to be retrieved
     * @return {@link Permission}
     */
    Permission getPermissionByName(String permissionName);

    /**
     * This method will be used to create {@link Permission} record(s) if none exists in the database
     */
    void createDefaultPermission();

}
