package com.edu.service.Impl;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.controller.CustomerController;
import com.edu.model.Customer;
import com.edu.repository.CustomerRespository;
import com.edu.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRespository customerRepository;
	
	private CustomerController customerController;

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerRepository.findAll();
	}

	@Override
	public boolean save(Customer customer) {
		if (customer != null) {
			customerRepository.save(customer);
			return true;
		}
		
		return false;
	}

	@Override
	public void deleteById(String id) {
		customerRepository.updateCustomer(id);
	}

	@Override
	public Page<Customer> listCuss(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (keyword != null) {
			return customerRepository.listCuss(keyword, pageable);
		}
		return customerRepository.listCussNoSearch(pageable);
	}

	@Override
	public Customer findById(String id) {
			return customerRepository.findById(id).get();

	}

	@Override
	public Optional<Customer> findOneByIdentityCard(String identityCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findIdentityCards() {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public boolean userExists(String username) {
		return customerRepository.findByUsername(username).isPresent();
	}
	
	public boolean userExistPhone(String phone) {
		return customerRepository.findByPhone(phone).isPresent();
	}
	
	public boolean userEmail(String email) {
		return customerRepository.findByEmail(email).isPresent();
	}
	public boolean userCard(String card) {
		return customerRepository.findByIdentity(card).isPresent();
	}
	

	

}
