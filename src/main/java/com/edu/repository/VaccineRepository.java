package com.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.dto.EmployeeDto;
import com.edu.dto.VaccineDto;
import com.edu.dto.VaccineDto1;
import com.edu.dto.VaccineTypeDto;
import com.edu.model.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String> {

	@Query("SELECT v FROM Vaccine v " + "	WHERE" + " v.vaccineId LIKE %?1%" + " OR v.vaccineName LIKE %?1%"
			+ " OR v.vaccineType.vaccineTypeName LIKE %?1%" + " OR v.numberOfInjection LIKE %?1%"
			// + " v.origin)"
			// + " LIKE %?1%"
			+ " ORDER BY v.vaccineId DESC")
	public Page<Vaccine> listVaccines(String keyword, Pageable pageable);

	public Page<Vaccine> findAllByOrderByVaccineIdDesc(Pageable pageable);

	@Query(value = "SELECT v FROM Vaccine v WHERE " + "v.vaccineId LIKE %:value% " + "OR v.vaccineName LIKE %:value% "
			+ "OR v.vaccineType.vaccineTypeName LIKE %:value% " + "OR (v.numberOfInjection + '') LIKE %:value% "
//			+ "OR ((v.isActive = true) AND ('Active' = :value)) "
//			+ "OR ((v.isActive = false ) AND ('In-Active' = :value)) "
			+ "OR v.origin LIKE %:value%")
	List<Vaccine> search(@Param("value") String value);

	@Query(value = "SELECT vaccine_id, vaccine_name  FROM vaccine", nativeQuery = true)
	List<Object[]> findVaccines();

	Optional<Vaccine> findOneByVaccineName(String vaccineName);

	@Query(value = "SELECT * FROM vaccine v WHERE v.vaccine_type_id = :vaccineTypeId", nativeQuery = true)
	List<Vaccine> findByVaccineTypeId(@Param("vaccineTypeId") String vaccineTypeId);

	/////////////////////////////////////////////////
	@Query("SELECT new com.edu.dto.VaccineDto1(v.vaccineId, v.vaccineName, v.vaccineType.vaccineTypeName, v.numberOfInjection, v.origin, v.active) "
			+ "FROM Vaccine v WHERE v.vaccineType.status = true")
	public Page<VaccineDto1> getPages(Pageable pageable);

	@Query("SELECT new com.edu.dto.VaccineDto1(v.vaccineId, v.vaccineName, v.vaccineType.vaccineTypeName, v.numberOfInjection, v.origin, v.active) "
			+ "FROM Vaccine v WHERE v.vaccineType.status = true AND "
			+ "CONCAT(v.vaccineId, v.vaccineName, v.vaccineType.vaccineTypeName, v.origin) LIKE %:keyword%")
	public Page<VaccineDto1> getPages(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT new com.edu.dto.VaccineDto1(v.vaccineId, v.vaccineName) " + "FROM Vaccine v WHERE v.active = true")
	List<VaccineDto1> findAllByIdAndName();
}
