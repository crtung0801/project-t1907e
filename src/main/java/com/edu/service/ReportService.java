package com.edu.service;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.edu.model.Customer;
import com.edu.model.InjectionResult;
import com.edu.model.Vaccine;

public interface ReportService {

	Page<Vaccine> reportVaccines(int pageNumber, String vaccineType, String origin, String startDate, String endDate);

	Map<Month, Integer> numberInjectionByMonth(Integer year, String typeName);

	public int countCustomer(int year, int month);

	public int countInjectResult(int year, int month);

	public List<Integer> listYear();

	Page<InjectionResult> findPaginated(int pageNo, int pageSize);

	Page<InjectionResult> filterPaginated(InjectionResult injectionResult, int pageNo, int pageSize);

	List<InjectionResult> findAll();

	Page<Customer> findPaginatedCus(int pageNo, int pageSize);

	Page<Customer> searchCustomerReport(int pageNo, int pageSize, Date fromDate, Date toDate, String fullName,
			String address);

}
