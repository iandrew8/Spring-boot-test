package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.core.setup.Currency;
import com.springboot.systems.system_wide.models.core.setup.CurrencyConversion;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, String> {
    Optional<CurrencyConversion> findCurrencyConversionByCurrency(Currency currency);

    Optional<CurrencyConversion> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
