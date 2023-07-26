package com.springboot.systems.test.models.core.security.annotations.roles;

import com.springboot.systems.test.models.core.security.Role;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RoleInterpreter {
	
	public static final List<Role> reflectivelyGetRoles() {

		List<Role> roles = new ArrayList<>();

		for (Field field : RoleConstants.class.getFields()) {

			if (field.isAnnotationPresent(RoleAnnotation.class)) {
				RoleAnnotation roleAnnotation = field.getAnnotation(RoleAnnotation.class);
				Role role = new Role();
				role.setName(roleAnnotation.name());
				role.setDescription(roleAnnotation.description());
				roles.add(role);
			}
		}
		return roles;
	}
	
}
