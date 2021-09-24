package com.edu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.model.Vaccine;
import com.edu.model.VaccineType;
@Repository
public interface ReportVaccineRepository extends PagingAndSortingRepository<Vaccine, String> {

	//@Query("SELECT DISTINCT vc FROM Vaccine vc INNER JOIN vc.vaccineType vt  "
//			+ "WHERE vc.timeBeginNextInjection BETWEEN ?1 AND ?2 "
//			+ "OR vt.vaccineTypeId = ?3 "
//			+ "OR vc.origin LIKE %?4% "
//			+ "AND vc.active = 1")
//	public Page<Vaccine> reportVaccines(String nextTime, String endTime,String vaccineType,  String origin,Pageable pageable);
	
	@Query(" SELECT v FROM Vaccine v INNER JOIN v.vaccineType vt WHERE vt.vaccineTypeId = :vaccineTypeId"
			+ " OR v.origin = :origin"
			+ " OR v.timeBeginNextInjection BETWEEN :startDate AND :endDate"
			+ "	AND v.active = 1 ")
	public Page<Vaccine> reportVaccines2(@Param("vaccineTypeId")String vaccineType, @Param("origin") String origin,
											@Param("startDate") String startDate ,@Param("endDate") String endDate,
											Pageable pageable);
	
	@Query("SELECT vc FROM Vaccine vc WHERE vc.active = 1")
	public Page<Vaccine> reportNoSearch(Pageable pageable);
}
