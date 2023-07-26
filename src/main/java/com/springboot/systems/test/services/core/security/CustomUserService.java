package com.springboot.systems.test.services.core.security;

import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.core.security.CustomUser;
import com.springboot.systems.test.models.core.security.Role;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.services.core.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmailAndRecordStatus(usernameOrEmail, usernameOrEmail, RecordStatus.ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException("User with username or email not found"));
		return getUserDetails(user);
	}
	
	private List<GrantedAuthority> getUserAuthorities(User user){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Role role: user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			grantedAuthorities.add(grantedAuthority);
		}
		return grantedAuthorities;
	}
	
	public CustomUser getUserDetails(final User user) {
        CustomUser userDetails = null;
        if (user != null) {
            List<GrantedAuthority> authorities = this.getUserAuthorities(user);
            if (authorities == null) {
                authorities = new ArrayList<GrantedAuthority>();
                System.out.println("ERROR: Authorities Not Loaded...");
            }
            userDetails = new CustomUser(user, true, true, true, true, authorities);
        }
        return userDetails;
    }	
}
