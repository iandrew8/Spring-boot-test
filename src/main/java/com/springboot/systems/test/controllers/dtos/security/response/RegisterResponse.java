package com.springboot.systems.test.controllers.dtos.security.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.security.UserProfile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse extends BaseResponse {

	private String token;
	private UserProfileResponse userProfile;

	public RegisterResponse(UserProfile userProfile, String token) {
		
		setId(userProfile.getId());
		setDateCreated(userProfile.getDateCreated());
		setDateChanged(userProfile.getDateChanged());
		setRecordStatus(userProfile.getRecordStatus());
		this.userProfile = new UserProfileResponse(userProfile);
		this.token = token;
	}
}
