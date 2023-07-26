package com.springboot.systems.test.models.core.security;

import com.springboot.systems.test.models.core.auditable.Auditable;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class Permission extends Auditable {

	private static final long serialVersionUID = -4578951881660721620L;
	private String name;
	private String description;

	public void validate() throws ValidationFailedException {
		Validate.notNull(this.getName(), "Permission name is required");
		Validate.notNull(this.getDescription(), "Permission description is required");
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	
	@Override
	public boolean equals(Object object) {
		return object instanceof Permission && (getId() != null) ? getId().equals(((Permission) object).getId())
				: (object == this);
	}

	@Override
	public int hashCode() {
		return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
	}	
}
