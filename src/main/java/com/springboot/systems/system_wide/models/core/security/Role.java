package com.springboot.systems.system_wide.models.core.security;

import com.springboot.systems.system_wide.models.core.security.annotations.roles.RoleConstants;
import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.services.utils.Validate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class Role extends Auditable {

	private static final long serialVersionUID = -4988616710392080545L;
	private String name;
	private String description;
	private Set<User> users;
	private Set<Permission> permissions;

	public void validate() throws ValidationFailedException {
		Validate.notNull(this, "Role details required");
		Validate.notNull(this.getName(), "Role name required");
		Validate.notEmpty(this.getPermissions(), "At least one permission is required");
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	@JsonIgnore //This will tell jackson not to serialize that property.
	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	@JsonIgnore //This will tell jackson not to serialize that property.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
			joinColumns = { @JoinColumn(name = "role_id") },
			inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	public void addPermission(final Permission permission) {
		if(this.permissions == null)
			this.permissions = new HashSet<>();
		this.permissions.add(permission);
    }
    
    public void removePermission(final Permission permission) {
    	if(this.permissions.contains(permission))
    		this.permissions.remove(permission);
    }
    
    public boolean hasPermission(final String perm) {
        if (this.permissions != null) {
            for (final Permission permission : this.permissions) {
                if (permission.getName().equalsIgnoreCase(perm)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Transient
    public boolean checkIfDefaultAdminRole() {
        return this.getName().equalsIgnoreCase(RoleConstants.ROLE_ADMIN);
    }

	@Override
	public String toString() {
		return this.name;
	}
	
	
	@Override
	public boolean equals(Object object) {
		return object instanceof Role && (getId() != null) ? getId().equals(((Role) object).getId())
				: (object == this);
	}

	@Override
	public int hashCode() {
		return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
	}	
}
