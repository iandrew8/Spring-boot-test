package com.springboot.systems.test.controllers.dtos.setup.response.list;

import com.springboot.systems.test.controllers.dtos.setup.response.CurrencyConversionResponse;
import com.springboot.systems.test.models.core.setup.CurrencyConversion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyConversionListResponse {
    private List<CurrencyConversionResponse> currencyConversionResponses = new ArrayList<>();

    public CurrencyConversionListResponse(List<CurrencyConversion> currencyConversions) {
        currencyConversions.forEach(currencyConversion ->
                this.currencyConversionResponses.add(new CurrencyConversionResponse(currencyConversion)));
    }
}
