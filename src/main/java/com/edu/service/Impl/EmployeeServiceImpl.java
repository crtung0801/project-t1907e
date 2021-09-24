package com.edu.service.Impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.dto.Account;
import com.edu.dto.EmployeeDto;
import com.edu.dto.UploadDto;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerEmailException;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.HandlerPhoneException;
import com.edu.exception.ResourceNotFoundException;
import com.edu.model.Employee;
import com.edu.repository.EmployeeRepository;
import com.edu.service.EmployeeService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;

@Service
public class EmployeeServiceImpl extends BaseDao implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Value("${avatar.base.folder}")
	private String uploadFolder;

	@Override
	public boolean delete (String id) {
		Employee employee = findById(id);
		if (employee.getIdOfRole() == 1) {
			return false;
		}
		employee.setStatus(0);
		employeeRepository.save(employee);
		return true;
	}

	@Override
	public Optional<Account> findByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

	@Override
	public Page<EmployeeDto> getPages(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (keyword == null || keyword.isBlank()) {
			return employeeRepository.getPages(pageable);
		}
		return employeeRepository.getPages(keyword, pageable);
	}

	@Override
	public Employee findById(String id) {
		
		Optional<Employee> optional = employeeRepository.findById(id);
		
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException(Constant.EMPLOYEE_NOT_FOUND + id);
		}
		
		return optional.get();
	}

	@Override
	public void save(Employee employee, UploadDto uploadDto, boolean isCreate) throws Exception {
		
		MultipartFile file = uploadDto.getFile();

		if (employee == null) {
			throw new IllegalArgumentException(Constant.NULL_EMPLOYEE);
		}
		
		if (!Utils.compare(LocalDate.parse(employee.getBirthDate()), LocalDate.now().minusYears(18))) {
			throw new HandlerDateException(Constant.INVALID_BIRTH_DATE_LESS_18);
		}
		
		if (!Utils.compare(LocalDate.parse(employee.getBirthDate()), LocalDate.now())) {
			throw new HandlerDateException(Constant.INVALID_BIRTH_DATE_GREATER_NOW);
		}
		
		if (file != null && !file.isEmpty()) {
			
			boolean matches = file.getOriginalFilename().matches(Constant.REGEX_IMAGE);
			
			if (!matches) {
				throw new HandlerFileException(Constant.INVALID_FILE_IMAGE);
			}
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFolder + employee.getEmployeeId() + employee.getFullName() + ".jpg");
			Files.write(path, bytes);
			employee.setImage(employee.getEmployeeId() + employee.getFullName() + ".jpg");
		}
		
		if (isCreate) {
			if (employeeRepository.existsById(employee.getEmployeeId())) {
				throw new HandlerIdException(Constant.EXIST_ID);
			}
			
			if (employeeRepository.existsByPhone(employee.getPhone())) {
				throw new HandlerPhoneException(Constant.EXIST_PHONE);
			}
			
			if (employeeRepository.existsByEmail(employee.getEmail())) {
				throw new HandlerEmailException(Constant.EXIST_EMAIL);
			}
			
		} else {
			Employee employeeDB = findById(employee.getEmployeeId());
			
			if (!employee.getPhone().equalsIgnoreCase(employeeDB.getPhone()) && employeeRepository.existsByPhone(employee.getPhone())) {
				throw new HandlerPhoneException(Constant.EXIST_PHONE);
			}
			
			if (!employee.getEmail().equalsIgnoreCase(employeeDB.getEmail()) && employeeRepository.existsByEmail(employee.getEmail())) {
				throw new HandlerEmailException(Constant.EXIST_EMAIL);
			}
		}
		
		employeeRepository.save(employee);
	}

	@Override
	public boolean existsById(String id) {
		return employeeRepository.existsById(id);
	}
}

