package com.springboot.systems.system_wide.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionPayload {
    private String name, description;
}
