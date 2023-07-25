package com.springboot.systems.system_wide.models.core.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUser extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = -4890146899128071010L;
	private User user;
	
	public CustomUser(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
