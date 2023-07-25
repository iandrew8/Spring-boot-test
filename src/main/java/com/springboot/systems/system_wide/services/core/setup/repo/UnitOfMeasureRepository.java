package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.core.setup.UnitOfMeasure;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, String> {
	Optional<UnitOfMeasure> findUnitOfMeasureByName(String name);
	Optional<UnitOfMeasure> findUnitOfMeasureByUnit(String unit);

    Optional<UnitOfMeasure> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
