package com.springboot.systems.system_wide.services.configs.jwt;

import com.springboot.systems.system_wide.services.core.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserService customUserService;
	
	/*
	 * What we do inside doFilterInternal():
	 * get JWT from the Authorization header (by removing Bearer prefix)
	 * if the request has JWT, validate it, parse username from it
	 * from username, get UserDetails to create an Authentication object
	 * set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		try {
			String jwtToken = parseJwt(request);
			
			// if token is valid configure Spring Security to manually set authentication
			if(jwtToken != null && jwtTokenUtil.validateJwtToken(jwtToken)) {
				String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				UserDetails userDetails = customUserService.loadUserByUsername(username);
//				System.out.println("User details " + userDetails.getUsername());
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
						UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);				
			}

		}catch (Exception e) {
			System.out.println("Cannot set user authentication: " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}	
}
