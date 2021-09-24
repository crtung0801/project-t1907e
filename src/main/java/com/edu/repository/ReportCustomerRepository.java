package com.edu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.edu.model.Customer;

@Repository
public interface ReportCustomerRepository
		extends PagingAndSortingRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

}
