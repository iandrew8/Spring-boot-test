package com.springboot.systems.test.controllers.dtos.security.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.security.Permission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionResponse extends BaseResponse {

    private String name, description;
    public PermissionResponse(Permission permission) {
        this.setId(permission.getId());
        this.setDateCreated(permission.getDateCreated());
        this.setDateChanged(permission.getDateChanged());
        this.setRecordStatus(permission.getRecordStatus());
        this.name = permission.getName();
        this.description = permission.getDescription();
    }
}
