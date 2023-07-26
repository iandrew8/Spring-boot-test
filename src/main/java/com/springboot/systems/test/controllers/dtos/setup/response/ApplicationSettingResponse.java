package com.springboot.systems.test.controllers.dtos.setup.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.setup.ApplicationSetting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationSettingResponse extends BaseResponse {
    private String clickUpPrivateKey, smartOLTAPIKey, systemEmail, systemEmailPassword, systemEmailHost, systemEmailPort,
            systemEmailSender;

    public ApplicationSettingResponse(ApplicationSetting applicationSetting) {
        this.setId(applicationSetting.getId());
        this.clickUpPrivateKey = applicationSetting.getClickUpPrivateKey();
        this.smartOLTAPIKey = applicationSetting.getSmartOLTAPIKey();
        this.systemEmail = applicationSetting.getSystemEmail();
        this.systemEmailPassword = applicationSetting.getSystemEmailPassword();
        this.systemEmailHost = applicationSetting.getSystemEmailHost();
        this.systemEmailPort = applicationSetting.getSystemEmailPort();
        this.systemEmailSender = applicationSetting.getSystemEmailSender();
        this.setDateCreated(applicationSetting.getDateCreated());
        this.setDateChanged(applicationSetting.getDateChanged());
        this.setRecordStatus(applicationSetting.getRecordStatus());
    }
}
