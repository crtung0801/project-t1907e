package com.edu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.dto.Account;
import com.edu.dto.EmployeeDto;
import com.edu.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	@Query("SELECT new com.edu.dto.EmployeeDto(e.employeeId, e.fullName, e.birthDate, e.gender, e.phone, e.address, e.image) FROM Employee e WHERE e.status = 1")
	public Page<EmployeeDto> getPages (Pageable pageable);
	
	@Query("SELECT new com.edu.dto.EmployeeDto(e.employeeId, e.fullName, e.birthDate, e.gender, e.phone, e.address, e.image) FROM Employee e WHERE e.status = 1 AND "
			+ "CONCAT(e.employeeId, e.fullName, e.gender, e.phone, e.address) LIKE %:keyword%")
	public Page<EmployeeDto> getPages (@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE e.status = 1 AND e.employeeId = :id")
	Optional<Employee> findById(@Param("id") String id);
	
	@Query("SELECT new com.edu.dto.Account(e.username, e.password, r.role) FROM Employee e INNER JOIN e.roles r WHERE e.username = :username AND e.status = 1")
	Optional<Account> findByUsername (@Param("username") String username);
	
	boolean existsByEmail(String email);
	
	boolean existsByPhone(String phone);
	
}
