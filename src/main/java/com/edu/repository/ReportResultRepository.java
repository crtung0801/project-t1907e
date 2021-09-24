package com.edu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.model.InjectionResult;
import com.edu.model.Vaccine;

@Repository
public interface ReportResultRepository
		extends PagingAndSortingRepository<InjectionResult, String> , JpaSpecificationExecutor<InjectionResult>{


	@Query("SELECT COUNT(DISTINCT customer) FROM InjectionResult WHERE (MONTH(injectionDate) = :month"
			+ "  AND YEAR(injectionDate) = :year)"
			+ "  OR"
			+ "  (MONTH(nextInjectionDate) = :month and YEAR(nextInjectionDate) = :year)")
	public int countByInjectResult(@Param("year") int year, @Param("month") int month);
	
	@Query("SELECT COUNT(1) FROM InjectionResult WHERE (MONTH(injectionDate) = :month"
			+ "  AND YEAR(injectionDate) = :year)")
	public int countInjectResult(@Param("year") int year, @Param("month") int month);

	@Query("  SELECT DISTINCT YEAR(injectionDate)"
			+ "FROM InjectionResult")
	public List<Integer> listYear();
	
	@Query("SELECT inR FROM InjectionResult inR  WHERE inR.status = 1 ")
	public Page<InjectionResult> reportNoSearch(Pageable pageable);
	
	@Query("SELECT  i FROM InjectionResult i INNER JOIN Prevention p ON p.id=i.prevention.id WHERE p.preventionName  LIKE '%' || :keyword || '%'")
	public Page<InjectionResult> searchPrevention(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT DISTINCT a FROM InjectionResult a INNER JOIN Vaccine b ON a.vaccine.vaccineId = b.vaccineId WHERE b.vaccineName LIKE '%' || :keyword || '%'")
	public Page<InjectionResult> searchVaccineInjectionResultByVaccineName(@Param("keyword") String keyword,
			Pageable pageable);
}
