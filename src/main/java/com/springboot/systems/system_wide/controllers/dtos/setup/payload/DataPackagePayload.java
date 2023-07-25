package com.springboot.systems.system_wide.controllers.dtos.setup.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataPackagePayload {
    private double quantity;
    private String unitOfMeasureId;
}
