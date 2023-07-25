package com.springboot.systems.system_wide.controllers.dtos.setup.payload;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyConversionPayload {
    private String currencyId;
    private BigDecimal oneDollarIsWorth;
}
