package com.springboot.systems.system_wide.models.core.security;

import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class User extends Auditable {

	private static final long serialVersionUID = -5539429957085163185L;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String phoneNumber;
//	private String facebookId;
	private String password;
	private UserProfile userProfile;
	private Set<Role> roles;

	public void validate() throws ValidationFailedException {
		Validate.notNull(this.getFirstName(), "First name is required");
		Validate.notNull(this.getLastName(), "Last name is required");
		Validate.notNull(this.getEmail(), "Email is required");
		Validate.notNull(this.getUsername(), "Username is required");
		Validate.notNull(this.getPhoneNumber(), "Phone number is required");
		Validate.notNull(this.getPassword(), "Password is required");
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@OneToOne(mappedBy = "user")
	public UserProfile getUserProfile() {
		return userProfile;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(final Role role) {
		if (this.roles == null)
			this.roles = new HashSet<>();
		this.roles.add(role);
	}

	public void removeRole(final Role role) {
		if (this.roles.contains(role))
			this.roles.remove(role);
	}
	
	@Transient
	public String getFullName() {
		return String.format("%s %s", this.firstName, this.lastName);
	}
	
	@Transient
	public boolean hasAdministrativePrivileges() {
		if (roles != null) {
			for (Role role : roles) {
				if (role.checkIfDefaultAdminRole()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Transient
	public boolean hasRole(final String roleName) {
		if (this.roles != null) {
			for (Role role : this.roles) {
				if (role.getName().equals(roleName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transient
	public boolean hasPermission(final String permissionName) {
		if (this.roles != null) {
			for (Role role : this.roles) {
				if (role.hasPermission(permissionName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.username;
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof User && (getId() != null) ? getId().equals(((User) object).getId()) : (object == this);
	}

	@Override
	public int hashCode() {
		return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
	}
}
