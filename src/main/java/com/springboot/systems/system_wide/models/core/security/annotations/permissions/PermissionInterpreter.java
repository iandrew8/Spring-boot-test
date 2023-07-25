package com.springboot.systems.system_wide.models.core.security.annotations.permissions;

import com.springboot.systems.system_wide.models.core.security.Permission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PermissionInterpreter {
	
	public static final List<Permission> reflectivelyGetPermissions() {

		List<Permission> permissions = new ArrayList<>();

		for (Field field : PermissionConstants.class.getFields()) {

			if (field.isAnnotationPresent(PermissionAnnotation.class)) {
				PermissionAnnotation permissionAnnotation = field.getAnnotation(PermissionAnnotation.class);
				Permission permission = new Permission();
				permission.setName(permissionAnnotation.name());
				permission.setDescription(permissionAnnotation.description());
				permissions.add(permission);
			}
		}
		return permissions;
	}
	
}
