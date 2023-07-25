package com.springboot.systems.system_wide.services.configs.security;

import com.springboot.systems.system_wide.services.configs.jpa.JpaCustomAnnotationMetadataUtil;
import com.springboot.systems.system_wide.services.configs.jwt.JwtAuthEntryPoint;
import com.springboot.systems.system_wide.services.configs.jwt.JwtRequestFilter;
import com.springboot.systems.system_wide.services.core.security.CustomUserService;
import com.googlecode.genericdao.search.MetadataUtil;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity // Is the primary spring security annotation that is used to enable web security in a project
@EnableGlobalMethodSecurity(prePostEnabled = true) // Used to enable method-level security based on annotations.
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * we are injecting our implementation of the users details service
     */
    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /*
     * A critical part of the registration process – password encoding – basically not storing the password in plaintext.
     * There are a few encoding mechanisms supported by Spring Security – and we'll use BCrypt,
     * as it's usually the best solution available.
     *
     * We'll start by defining the simple BCryptPasswordEncoder as a bean in our configuration:
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JPASearchProcessor jpaSearchProcessor() {
        return new JPASearchProcessor(metadataUtil());
    }

    @Bean
    MetadataUtil metadataUtil() {
        return new JpaCustomAnnotationMetadataUtil();
    }

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
            {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Permit all http requests
//        http.authorizeRequests().anyRequest().permitAll();

        // Disable csrf Protection because it is enabled by default in spring security
        // Permit all get requests
        // Permit all requests with /auth/ pattern
        // Permit all authenticated requests
        // Use Basic authentication
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Don't authenticate this request
                .anyRequest().authenticated() // Authenticate other requests
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and() // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.logout()
//                .logoutUrl("/auth/logout")
//                .logoutSuccessUrl("/auth/login")
//                .invalidateHttpSession(true)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));

        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
        );

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/api/v1/clickup-endpoints/clickup/hello-world",
                "/api/v1/clickup-webhooks/manage-task-moved-event",
                "/api/v1/requests/post",
                "/api/v1/isps/fetch",
                "/api/v1/responses/fetch",
                "/api/v1/service-zones/fetch",
                "/api/v1/clickup-webhooks/manage-task-status-updated-event",
                "/api/v1/isp-clients/access-key/**",
                "/api/v1/data-packages/access-key/**",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}