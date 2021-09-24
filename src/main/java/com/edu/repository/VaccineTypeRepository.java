package com.edu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.edu.dto.VaccineTypeDto;
import com.edu.model.VaccineType;

@Repository
public interface VaccineTypeRepository extends PagingAndSortingRepository<VaccineType, String> {

	@Query("SELECT vt FROM VaccineType vt " 
			+ "	WHERE CONCAT(vt.vaccineTypeId, vt.vaccineTypeName, vt.description)"
			+ "	LIKE %?1%" 
			+ " ORDER BY vt.vaccineTypeId DESC")
	public Page<VaccineType> listVaccineTypes(String keyword, Pageable pageable);
	
	public Page<VaccineType> findAllByOrderByVaccineTypeIdDesc(Pageable pageable);
	
	public List<VaccineType> findAll();
	
	@Query("SELECT vt.vaccineTypeId FROM VaccineType vt WHERE vt.status =1")
	List<String> idVc();

	//////////////////////////////////////////////
	
	@Query("SELECT new com.edu.dto.VaccineTypeDto(vt.vaccineTypeId, vt.vaccineTypeName, vt.description, vt.status, vt.image) FROM VaccineType vt " 
			+ "	WHERE CONCAT(vt.vaccineTypeId, vt.vaccineTypeName, vt.description)"
			+ "	LIKE %?1%")
	Page<VaccineTypeDto> getPages(String keyword, Pageable pageable);
	
	@Query("SELECT new com.edu.dto.VaccineTypeDto(vt.vaccineTypeId, vt.vaccineTypeName, vt.description, vt.status, vt.image) FROM VaccineType vt") 
	Page<VaccineTypeDto> getPages(Pageable pageable);
	
	@Query("SELECT new com.edu.dto.VaccineTypeDto(vt.vaccineTypeId, vt.vaccineTypeName) FROM VaccineType vt WHERE vt.status = true")
	List<VaccineTypeDto> findAllByIdAndName();
}
