package com.springboot.systems.test.models.core.security.annotations.roles;

public final class RoleConstants {

	@RoleAnnotation(name = "System Administrator", description = "Has role to manage the system")
	public static final String ROLE_ADMIN = "System Administrator";

	@RoleAnnotation(name = "Normal User", description = "Has role to use the system")
	public static final String ROLE_USER = "Normal User";

	@RoleAnnotation(name = "ISP Admin", description = "Has role of ISP Admin")
	public static final String ROLE_ISP_ADMIN = "ISP Admin";
}
