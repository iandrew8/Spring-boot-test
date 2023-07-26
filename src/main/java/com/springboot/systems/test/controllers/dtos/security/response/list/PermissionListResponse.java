package com.springboot.systems.test.controllers.dtos.security.response.list;

import com.springboot.systems.test.controllers.dtos.security.response.PermissionResponse;
import com.springboot.systems.test.models.core.security.Permission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PermissionListResponse {
    private List<PermissionResponse> records = new ArrayList<>();

    public PermissionListResponse(Set<Permission> permissions) {
        permissions.forEach(permission ->
                this.records.add(new PermissionResponse(permission)));
    }
}
