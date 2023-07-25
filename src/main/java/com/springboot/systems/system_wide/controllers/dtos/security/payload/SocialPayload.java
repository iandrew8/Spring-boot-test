package com.springboot.systems.system_wide.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialPayload {

    private String token, username, email, firstName, lastName, facebookId, password;

}
