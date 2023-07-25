package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.core.setup.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

	Optional<Currency> findCurrencyByNameAndRecordStatus(String name, RecordStatus recordStatus);

	Optional<Currency> findCurrencyByUnitAndRecordStatus(String unit, RecordStatus recordStatus);

	Optional<Currency> findByIdAndRecordStatus(String id, RecordStatus recordStatus);

}
