package com.springboot.systems.test.models.core.security.annotations.roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAnnotation {

	String name();

	String description() default "Not supplied";

}
