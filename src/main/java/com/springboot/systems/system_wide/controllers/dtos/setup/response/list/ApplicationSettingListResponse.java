package com.springboot.systems.system_wide.controllers.dtos.setup.response.list;

import com.springboot.systems.system_wide.controllers.dtos.setup.response.ApplicationSettingResponse;
import com.springboot.systems.system_wide.models.core.setup.ApplicationSetting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationSettingListResponse {
    private List<ApplicationSettingResponse> records = new ArrayList<>();

    public ApplicationSettingListResponse(List<ApplicationSetting> applicationSettings) {
        applicationSettings.forEach(applicationSetting ->
                this.records.add(new ApplicationSettingResponse(applicationSetting)));
    }
}
