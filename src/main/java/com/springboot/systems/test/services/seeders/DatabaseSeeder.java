package com.springboot.systems.test.services.seeders;

import com.springboot.systems.test.services.core.security.PermissionService;
import com.springboot.systems.test.services.core.security.RoleService;
import com.springboot.systems.test.services.core.security.UserService;
import com.springboot.systems.test.services.core.setup.ApplicationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final ApplicationSettingService applicationSettingService;

    @EventListener
    public void seedDatabase(ContextRefreshedEvent event) {
        permissionService.createDefaultPermission();
        roleService.createDefaultRole();
        userService.createDefaultUser();
        applicationSettingService.createDefaultApplicationSetting();
    }
}
