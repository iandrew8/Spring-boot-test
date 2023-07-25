package com.springboot.systems.system_wide.services.core.setup;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.models.core.setup.DataPackage;
import com.springboot.systems.system_wide.models.core.setup.UnitOfMeasure;

import java.util.List;

public interface DataPackageService extends GenericService<DataPackage> {

    /**
     * Returns all {@link DataPackage}s attached to the supplied {@link UnitOfMeasure}
     *
     * @param unitOfMeasure, the {@link UnitOfMeasure} whose {@link DataPackage}s are to be returned
     * @return {@link List<DataPackage>}
     */
    List<DataPackage> getDataPackagesByUnitOfMeasure(UnitOfMeasure unitOfMeasure);

    /**
     * Returns the {@link DataPackage} with a quantity similar to what has been supplied
     *
     * @param quantity, the quantity to be matched
     * @return {@link List<DataPackage>}
     */
    List<DataPackage> getDataPackageByQuantity(double quantity) throws ValidationFailedException, OperationFailedException;

    /**
     * Returns a {@link DataPackage} with a matching quantity and {@link UnitOfMeasure}
     *
     * @param quantity,      the quantity to be matched
     * @param unitOfMeasure, the {@link UnitOfMeasure} to be matched
     * @return {@link DataPackage}
     */
    DataPackage getDataPackageByQuantityAndUnitOfMeasure(double quantity, UnitOfMeasure unitOfMeasure);

    /**
     * This method will create {@link DataPackage} record(s) if none exist in the database
     */
    void createDefaultDataPackage();
}
