package com.springboot.systems.system_wide.services.configs.jpa;

import com.springboot.systems.system_wide.models.core.security.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
 * @EnableJpaAuditing will enable the Spring Data JPA Auditing features for the application.  
 * This annotation has the capability of managing (persisting and updating) the date related audit fields  
 * such as @CreatedDate and @LastModifiedDate.
 * 
 * But there are some user related audit fields such as @CreatedBy and @LastModifiedBy 
 * will require the information of the currently logged in user. In order to manage (persist and update) those fields, 
 * we need to give a way to access the currently logged user for the  JPA auditing feature.
 * 
 * To tell Spring Data JPA about currently logged-in users, 
 * we will need to provide an implementation of AuditorAware interface and override the getCurrentAuditor() method. 
 * getCurrentAuditor() should implement to return the currently logged-in user.
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfiguration {
	
    @Bean
    public AuditorAware<User> auditorAware() {
        return new AuditAwareImpl();
    }

}
