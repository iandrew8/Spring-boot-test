package com.springboot.systems.test.services.utils;

import com.springboot.systems.test.models.core.security.CustomUser;
import com.springboot.systems.test.models.core.security.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomSecurityUtil {

	public static User getCurrentlyLoggedInUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication() != null) {
			if (securityContext.getAuthentication().getPrincipal() != null
					&& securityContext.getAuthentication().getPrincipal() instanceof CustomUser) {
				System.out.println("Logged in user " + securityContext.getAuthentication().getPrincipal());

				CustomUser customUser = (CustomUser) securityContext.getAuthentication().getPrincipal();
				return customUser.getUser();
			}
		}
		return null;
	}

}
