package com.springboot.systems.test.controllers.dtos.setup.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.setup.DataPackage;
import com.springboot.systems.test.models.core.setup.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataPackageResponse extends BaseResponse {
    private double quantity;
    private UnitOfMeasure unitOfMeasure;

    public DataPackageResponse(DataPackage aDataPackage) {
        this.setId(aDataPackage.getId());
        this.quantity = aDataPackage.getQuantity();
        this.unitOfMeasure = aDataPackage.getUnitOfMeasure();
        this.setDateCreated(aDataPackage.getDateCreated());
        this.setDateChanged(aDataPackage.getDateChanged());
        this.setRecordStatus(aDataPackage.getRecordStatus());
    }
}
