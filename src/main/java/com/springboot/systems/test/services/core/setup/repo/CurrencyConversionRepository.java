package com.springboot.systems.test.services.core.setup.repo;

import com.springboot.systems.test.models.core.setup.Currency;
import com.springboot.systems.test.models.core.setup.CurrencyConversion;
import com.springboot.systems.test.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, String> {
    Optional<CurrencyConversion> findCurrencyConversionByCurrency(Currency currency);

    Optional<CurrencyConversion> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
