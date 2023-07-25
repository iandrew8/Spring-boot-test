package com.springboot.systems.system_wide.controllers.dtos.setup.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationSettingPayload {
    private String clickUpPrivateKey, smartOLTAPIKey, currencyId, systemEmail, systemEmailPassword, systemEmailHost,
            systemEmailPort, systemEmailSender, googleDriveFolderId;
}
