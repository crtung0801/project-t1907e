package com.edu.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.edu.dto.Account;
import com.edu.dto.EmployeeDto;
import com.edu.dto.UploadDto;
import com.edu.model.Employee;

public interface EmployeeService {
	
	boolean delete(String id);
	
	Employee findById(String id);
	
	Optional<Account> findByUsername(String username);
	
	Page<EmployeeDto> getPages(int pageNumber, String keyword);
	
	void save(Employee employee, UploadDto uploadDto, boolean isCreate) throws Exception;
	
	boolean existsById(String id);
}
