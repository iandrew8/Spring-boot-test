package com.springboot.systems.system_wide.services.core.setup.repo;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.core.setup.DataPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataPackageRepository extends JpaRepository<DataPackage, String> {
	Optional<DataPackage> findDataPackageByQuantity(double quantity);

	Optional<DataPackage> findDataPackagesByRecordStatus(RecordStatus recordStatus);

    Optional<DataPackage> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
