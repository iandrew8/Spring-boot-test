package com.springboot.systems.system_wide.controllers.dtos.security.response;

import com.springboot.systems.system_wide.controllers.dtos.BaseResponse;
import com.springboot.systems.system_wide.models.core.security.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileResponse extends BaseResponse {

	private UserResponse user;

	public UserProfileResponse(UserProfile userProfile) {
		this.setId(userProfile.getId());
		this.setDateCreated(userProfile.getDateCreated());
		this.setDateChanged(userProfile.getDateChanged());
		this.setRecordStatus(userProfile.getRecordStatus());
		this.user = new UserResponse(userProfile.getUser());
	}
}
