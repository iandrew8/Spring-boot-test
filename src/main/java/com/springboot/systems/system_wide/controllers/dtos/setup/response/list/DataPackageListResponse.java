package com.springboot.systems.system_wide.controllers.dtos.setup.response.list;

import com.springboot.systems.system_wide.controllers.dtos.setup.response.DataPackageResponse;
import com.springboot.systems.system_wide.models.core.setup.DataPackage;
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
