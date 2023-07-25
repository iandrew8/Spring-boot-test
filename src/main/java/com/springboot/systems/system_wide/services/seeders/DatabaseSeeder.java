package com.springboot.systems.system_wide.services.seeders;

import com.springboot.systems.system_wide.services.core.emailtemplate.EmailTemplateService;
import com.springboot.systems.system_wide.services.core.security.PermissionService;
import com.springboot.systems.system_wide.services.core.security.RoleService;
import com.springboot.systems.system_wide.services.core.security.UserService;
import com.springboot.systems.system_wide.services.core.setup.ApplicationSettingService;
import com.springboot.systems.system_wide.services.core.setup.CurrencyConversionService;
import com.springboot.systems.system_wide.services.core.setup.CurrencyService;
import com.springboot.systems.system_wide.services.core.setup.UnitOfMeasureService;
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
    private final CurrencyService currencyService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final ApplicationSettingService applicationSettingService;
    private final EmailTemplateService emailTemplateService;
    private final CurrencyConversionService currencyConversionService;

    @EventListener
    public void seedDatabase(ContextRefreshedEvent event) {
        permissionService.createDefaultPermission();
        roleService.createDefaultRole();
        userService.createDefaultUser();
        currencyService.createDefaultCurrency();
        unitOfMeasureService.createDefaultUnitOfMeasure();
        applicationSettingService.createDefaultApplicationSetting();
        emailTemplateService.createDefaultEmailTemplate();
        currencyConversionService.createDefaultCurrencyConversion();
    }
}
