package com.springboot.systems.system_wide.models.core.security.annotations.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {

	String name();

	String description() default "Not supplied";

}
