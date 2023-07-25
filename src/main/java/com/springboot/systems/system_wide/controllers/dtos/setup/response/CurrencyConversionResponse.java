package com.springboot.systems.system_wide.controllers.dtos.setup.response;

import com.springboot.systems.system_wide.controllers.dtos.BaseResponse;
import com.springboot.systems.system_wide.models.core.setup.Currency;
import com.springboot.systems.system_wide.models.core.setup.CurrencyConversion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyConversionResponse extends BaseResponse {
    private Currency currency;
    private BigDecimal oneDollarIsWorth;

    public CurrencyConversionResponse(CurrencyConversion currencyConversion) {
        this.setId(currencyConversion.getId());
        this.setDateCreated(currencyConversion.getDateCreated());
        this.setDateChanged(currencyConversion.getDateChanged());
        this.setRecordStatus(currencyConversion.getRecordStatus());
        this.currency = currencyConversion.getCurrency();
        this.oneDollarIsWorth = currencyConversion.getOneDollarIsWorth();
    }

}
