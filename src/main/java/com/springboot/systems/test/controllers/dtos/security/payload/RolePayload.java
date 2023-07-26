package com.springboot.systems.test.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolePayload {
	
	private String name, description;
	private List<String> permissionIds;
}
