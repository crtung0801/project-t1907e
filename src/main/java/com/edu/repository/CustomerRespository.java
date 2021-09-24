package com.edu.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.model.Customer;

@Repository
public interface CustomerRespository extends PagingAndSortingRepository<Customer, String> {

	@Query("Select cu From Customer cu "
			+ "	Where CONCAT(cu.customerId,cu.address,cu.fullName,cu.email,cu.identityCard)"
			+ "	LIKE %?1% AND cu.status =1 " + " order by cu.customerId desc")
	public Page<Customer> listCuss(String keyword, Pageable pageable);

	@Query("Select cu From Customer cu WHERE cu.status =1 order by cu.customerId desc")
	public Page<Customer> listCussNoSearch(Pageable pageable);

	@Query("Select cu From Customer cu WHERE cu.status =1 order by cu.customerId desc")
	public List<Customer> findCus();

	@Query("Select cu.username From Customer cu WHERE cu.username= ?1")
	public Optional<Customer> findByUsername(String username);

	@Query("Select cu.phone From Customer cu WHERE cu.phone= ?1")
	public Optional<Customer> findByPhone(String phone);

	@Query("Select cu.email From Customer cu WHERE cu.email= ?1")
	public Optional<Customer> findByEmail(String email);

	@Query("Select cu.identityCard From Customer cu WHERE cu.identityCard= ?1")
	public Optional<Customer> findByIdentity(String identity);

	@Transactional
	@Modifying
	@Query("Update Customer cu Set cu.status = 0 Where cu.customerId = :id")
	void updateCustomer(@Param("id") String id);
}
