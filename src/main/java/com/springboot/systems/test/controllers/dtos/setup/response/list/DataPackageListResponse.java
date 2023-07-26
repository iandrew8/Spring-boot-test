package com.springboot.systems.test.controllers.dtos.setup.response.list;

import com.springboot.systems.test.controllers.dtos.setup.response.DataPackageResponse;
import com.springboot.systems.test.models.core.setup.DataPackage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataPackageListResponse {
    private List<DataPackageResponse> records = new ArrayList<>();

    public DataPackageListResponse(List<DataPackage> dataPackages) {
        dataPackages.forEach(dataPackage -> this.records.add(new DataPackageResponse(dataPackage)));
    }
}
