package com.springboot.systems.system_wide.controllers.dtos.setup.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyPayload {
    private String name, unit;
}
