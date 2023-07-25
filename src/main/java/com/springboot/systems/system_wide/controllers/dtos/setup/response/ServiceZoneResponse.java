package com.springboot.systems.system_wide.controllers.dtos.setup.response;

import com.springboot.systems.system_wide.controllers.dtos.BaseResponse;
import com.springboot.systems.system_wide.models.core.setup.ServiceZone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceZoneResponse extends BaseResponse {
    private String name, nrcDiscountStatus;

    public ServiceZoneResponse(ServiceZone serviceZone) {
        this.setId(serviceZone.getId());
        this.setDateCreated(serviceZone.getDateCreated());
        this.setDateChanged(serviceZone.getDateChanged());
        this.setRecordStatus(serviceZone.getRecordStatus());
        this.name = serviceZone.getName();
        this.nrcDiscountStatus = serviceZone.getNRCDiscountStatus().getName();
    }

}
