package com.edu.service;

import java.util.Optional;

import com.edu.model.Role;

public interface RoleService {
	Role saveRole (Role role);

	Role findById (Long id);
}
