package com.springboot.systems.system_wide.controllers.dtos.security.response.list;

import com.springboot.systems.system_wide.controllers.dtos.security.response.UserProfileResponse;
import com.springboot.systems.system_wide.models.core.security.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileListResponse {

	private List<UserProfileResponse> records = new ArrayList<>();

	public UserProfileListResponse(List<UserProfile> userProfiles) {
		userProfiles.forEach(userProfile -> this.records.add(new UserProfileResponse(userProfile)));
	}
	
}
