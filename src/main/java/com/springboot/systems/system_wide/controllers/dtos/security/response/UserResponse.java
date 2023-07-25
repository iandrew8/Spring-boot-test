package com.springboot.systems.system_wide.controllers.dtos.security.response;

import com.springboot.systems.system_wide.controllers.dtos.BaseResponse;
import com.springboot.systems.system_wide.controllers.dtos.security.response.list.RoleListResponse;
import com.springboot.systems.system_wide.models.core.security.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponse extends BaseResponse {

	private String username, email, phoneNumber, firstName, lastName, fullName;
	private List<RoleResponse> roles;

	public UserResponse() {}

	public UserResponse(User user) {
		this.setId(user.getId());
		this.setDateCreated(user.getDateCreated());
		this.setDateChanged(user.getDateChanged());
		this.setRecordStatus(user.getRecordStatus());
		this.roles = new RoleListResponse(new ArrayList<>(user.getRoles())).getRecords();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.phoneNumber = user.getPhoneNumber();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.fullName = user.getFullName();
	}
}
