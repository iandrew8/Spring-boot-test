package com.springboot.systems.test.controllers.dtos.security.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.security.Permission;
import com.springboot.systems.test.models.core.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse extends BaseResponse {
	
	private String name, description;
	private Set<Permission> permissions = new HashSet<>();
	
	public RoleResponse(Role role) {
		this.setId(role.getId());
		this.setDateCreated(role.getDateCreated());
		this.setDateChanged(role.getDateChanged());
		this.setRecordStatus(role.getRecordStatus());
		this.name = role.getName();
		this.description = role.getDescription();
		this.permissions = role.getPermissions();
	}

}
