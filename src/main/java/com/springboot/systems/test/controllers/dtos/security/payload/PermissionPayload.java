package com.springboot.systems.test.controllers.dtos.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionPayload {
    private String name, description;
}
