package com.springboot.systems.system_wide.services.core.setup;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.models.core.setup.Currency;

public interface CurrencyService extends GenericService<Currency> {

    /**
     * Returns the {@link Currency} with a name and unit supplied
     *
     * @param name, the name of the {@link Currency}
     * @param unit, the unit of the {@link Currency}
     * @return {@link Currency}
     */
    Currency getCurrencyByNameAndUnit(String name, String unit);

    /**
     * This method will create {@link Currency} record(s) if none exists in the database
     */
    void createDefaultCurrency();

}
