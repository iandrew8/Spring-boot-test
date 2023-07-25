package com.springboot.systems.system_wide.services.configs.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * This class rejects unauthenticated request and send error code 401
	 */

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		System.out.println("Error: Un authorized error: " + arg2.getMessage());
		arg1.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}
	
}
