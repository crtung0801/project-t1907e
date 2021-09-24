	package com.edu.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.dto.Account;
import com.edu.model.CustomUserDetails;
import com.edu.model.Employee;
import com.edu.model.Role;
import com.edu.service.EmployeeService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> optional = employeeService.findByUsername(username);
		
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException("Not Found User!");
		}
		
		Account account = optional.get();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
//		for (Role role : account.getRoles()) {
//			String roleName = "ROLE_" + role.getRole();
//			authorities.add(new SimpleGrantedAuthority(roleName));
//		}
		
		String roleName = "ROLE_" + account.getRole();
		authorities.add(new SimpleGrantedAuthority(roleName));
		
		UserDetails userDetails = new CustomUserDetails(account.getUsername(), account.getPassword(), authorities);
		
		return userDetails;
	}

}
