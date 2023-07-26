package com.springboot.systems.test.models.core.security;

import com.springboot.systems.test.models.core.auditable.Auditable;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_profiles")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class UserProfile extends Auditable {

	private static final long serialVersionUID = -2639679192913679525L;
	private User user;

	public void validate() throws ValidationFailedException {
		Validate.notNull(this, "User profile details required");
		Validate.notNull(this.getUser(), "Authentication details required");
	}
	
	public UserProfile() {}
	
	public UserProfile(User user) {
		this.user = user;
	}
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return this.user.toString();
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof UserProfile && (getId() != null) ? getId().equals(((UserProfile) object).getId()) : (object == this);
	}

	@Override
	public int hashCode() {
		return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
	}

}
