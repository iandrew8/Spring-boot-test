package com.springboot.systems.test.services.core.setup;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.models.core.setup.CurrencyConversion;

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
