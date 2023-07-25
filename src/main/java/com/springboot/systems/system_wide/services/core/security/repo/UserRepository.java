package com.springboot.systems.system_wide.services.core.security.repo;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.core.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmailAndRecordStatus(String username, String email, RecordStatus recordStatus);
	Optional<User> findByUsernameAndRecordStatus(String username, RecordStatus recordStatus);
	Optional<User> findByIdAndRecordStatus(String id, RecordStatus recordStatus);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByPhoneNumber(String phoneNumber);
	/*Boolean existsByFacebookId(String facebookId);
	Optional<User> findByFacebookId(String facebookId);*/
}
