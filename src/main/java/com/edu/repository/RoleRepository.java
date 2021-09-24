package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edu.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(name = "queryfindRoleById")
	Role findRoleById(Long id);

}
