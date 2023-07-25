package com.springboot.systems.system_wide.controllers.dtos.security.response;

import com.springboot.systems.system_wide.models.core.security.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse extends UserResponse {
		
	private String token;

	public LoginResponse(User user, String token) {
		setId(user.getId());
		setUsername(user.getUsername());
		setEmail(user.getEmail());
		setPhoneNumber(user.getPhoneNumber());
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setFullName(user.getFullName());
		this.token = token;
	}
}
