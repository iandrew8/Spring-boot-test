package com.springboot.systems.test.services.core.setup;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.models.core.setup.UnitOfMeasure;

public interface UnitOfMeasureService extends GenericService<UnitOfMeasure> {

    /**
     * Returns the {@link UnitOfMeasure} with the unit matching the one that has been supplied
     *
     * @param unit, the unit of measure
     * @return {@link UnitOfMeasure}
     */
    UnitOfMeasure getUnitOfMeasureByUnit(String unit);

    /**
     * Returns the {@link UnitOfMeasure} with the name and unit matching the ones that have been supplied
     *
     * @param name, the name of the unit of measure
     * @param unit, the unit of measure
     * @return {@link UnitOfMeasure}
     */
    UnitOfMeasure getUnitOfMeasureByUnitAndName(String name, String unit);

    /**
     * This method creates a default {@link UnitOfMeasure} record(s) if it does not exist in the database
     */
    void createDefaultUnitOfMeasure();
}
