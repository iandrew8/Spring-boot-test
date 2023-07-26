package com.springboot.systems.test.services.core.security.repo;

import com.springboot.systems.test.models.core.security.UserProfile;
import com.springboot.systems.test.models.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
}
