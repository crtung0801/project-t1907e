package com.edu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.model.Customer;

public interface CustomerService {
	
	
	// find by identity card
	Optional<Customer> findOneByIdentityCard(String identityCard);
	
	// findAll
	List<Customer> findAll();
	
	// find all by identity card
	List<String> findIdentityCards();
	
	
	 Page<Customer> listCuss(int pageNumber,String keyword);
	
	// save
	boolean save(Customer customer);
	
	Customer findById(String id);
	// delete by id
	void deleteById(String id);
	

}
