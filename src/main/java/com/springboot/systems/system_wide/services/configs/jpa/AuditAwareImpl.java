package com.springboot.systems.system_wide.services.configs.jpa;

import com.springboot.systems.system_wide.models.core.security.CustomUser;
import com.springboot.systems.system_wide.models.core.security.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/*
 * To tell Spring Data JPA about currently logged-in users, 
 * we will need to provide an implementation of AuditorAware interface and override the getCurrentAuditor() method. 
 * getCurrentAuditor() should implement to return the currently logged-in user.
 * 
 */

public class AuditAwareImpl implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication() != null) {
			if (securityContext.getAuthentication().getPrincipal() != null
					&& securityContext.getAuthentication().getPrincipal() instanceof CustomUser) {
				System.out.println("Auditable user " + securityContext.getAuthentication().getPrincipal());

				CustomUser customUser = (CustomUser) securityContext.getAuthentication().getPrincipal();
				return Optional.of(customUser.getUser());
			}
		}
		return null;
	}
}
