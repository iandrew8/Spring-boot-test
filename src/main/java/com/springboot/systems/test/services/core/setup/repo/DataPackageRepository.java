package com.springboot.systems.test.services.core.setup.repo;

import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.core.setup.DataPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataPackageRepository extends JpaRepository<DataPackage, String> {
	Optional<DataPackage> findDataPackageByQuantity(double quantity);

	Optional<DataPackage> findDataPackagesByRecordStatus(RecordStatus recordStatus);

    Optional<DataPackage> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
