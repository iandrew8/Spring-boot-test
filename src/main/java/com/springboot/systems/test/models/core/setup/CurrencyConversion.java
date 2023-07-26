package com.springboot.systems.test.models.core.setup;

import com.springboot.systems.test.models.core.auditable.Auditable;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * It will hold all currency conversions based on one dollar.
 * It will be used in the converting of the dollar(default currency of the system) to different currencies.
 * @author ttc
 */
@Entity
@Table(name = "currency_conversions")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class CurrencyConversion extends Auditable {
    private BigDecimal oneDollarIsWorth;
    private Currency currency;

    public void validate() throws ValidationFailedException {
        Validate.notNull(this.getCurrency(), "Currency is required");
        Validate.notNull(this.getOneDollarIsWorth(), "Rate is required");
    }

    public CurrencyConversion() {
        this.oneDollarIsWorth = BigDecimal.ZERO;
    }

    @Column(name = "one_dollar_is_worth", columnDefinition = "Decimal(20,2) default'0.00'")
    public BigDecimal getOneDollarIsWorth() {
        return oneDollarIsWorth;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return this.oneDollarIsWorth.toString();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CurrencyConversion && (getId() != null) ? getId().equals(((CurrencyConversion) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
