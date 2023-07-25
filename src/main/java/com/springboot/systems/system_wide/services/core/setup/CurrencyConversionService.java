package com.springboot.systems.system_wide.services.core.setup;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.models.core.setup.CurrencyConversion;

public interface CurrencyConversionService extends GenericService<CurrencyConversion> {

    /**
     * Find currency conversion by currency unit
     * @param unit, currency unit
     * @return CurrencyConversion
     */
    CurrencyConversion findByCurrencyUnit(String unit);

    /**
     * This method will be used to create the default {@link CurrencyConversion} if it does not exist in the database
     */
    void createDefaultCurrencyConversion();
}
