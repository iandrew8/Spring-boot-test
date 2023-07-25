package com.springboot.systems.system_wide.controllers.dtos.security.response.list;

import com.springboot.systems.system_wide.controllers.dtos.security.response.RoleResponse;
import com.springboot.systems.system_wide.models.core.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleListResponse {
	
	private List<RoleResponse> records = new ArrayList<>();
	
	public RoleListResponse(List<Role> roleData) {
		roleData.forEach(role -> this.records.add(new RoleResponse(role)));
	}
}
