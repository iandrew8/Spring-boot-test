package com.springboot.systems.test.controllers.dtos.security.response.list;

import com.springboot.systems.test.controllers.dtos.security.response.UserResponse;
import com.springboot.systems.test.models.core.security.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserListResponse {
		
	private List<UserResponse> records = new ArrayList<>();
	
	public UserListResponse(List<User> users) {
		users.forEach(user -> this.records.add(new UserResponse(user)));
	}
	
}
