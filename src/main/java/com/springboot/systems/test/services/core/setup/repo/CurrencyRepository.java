package com.springboot.systems.test.services.core.setup.repo;

import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.core.setup.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

	Optional<Currency> findCurrencyByNameAndRecordStatus(String name, RecordStatus recordStatus);

	Optional<Currency> findCurrencyByUnitAndRecordStatus(String unit, RecordStatus recordStatus);

	Optional<Currency> findByIdAndRecordStatus(String id, RecordStatus recordStatus);

}
