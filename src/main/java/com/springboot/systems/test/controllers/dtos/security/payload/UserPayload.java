package com.springboot.systems.test.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPayload {
	private String username, email, password, phoneNumber, firstName, lastName;
	private List<String> roleIds;

}
