package com.springboot.systems.test.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPayload {
	private String usernameOrEmail, password;
}
