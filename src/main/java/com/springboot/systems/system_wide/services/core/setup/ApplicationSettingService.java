package com.springboot.systems.system_wide.services.core.setup;

import com.springboot.systems.system_wide.models.core.setup.ApplicationSetting;
import com.springboot.systems.system_wide.services.dao.GenericService;

public interface ApplicationSettingService extends GenericService<ApplicationSetting> {

    /**
     * Returns the active {@link ApplicationSetting} and there is only meant to be one
     *
     * @return {@link ApplicationSetting}
     */
    ApplicationSetting getActiveApplicationSetting();

    /**
     * This method creates a default {@link ApplicationSetting} record(s) if none exists in the database
     */
    void createDefaultApplicationSetting();

}
