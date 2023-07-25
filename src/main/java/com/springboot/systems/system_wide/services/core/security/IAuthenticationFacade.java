package com.springboot.systems.system_wide.services.core.security;

import org.springframework.security.core.Authentication;

/**
 * @author ttc
 * Service to help deal with retrieving the logged-in user
 */
public interface IAuthenticationFacade {

    /**
     * Returns the logged-in user's username
     * @return
     */
    Authentication getAuthentication();

}
