package com.edu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.model.Employee;
import com.edu.model.InjectionResult;

@Repository
public interface ResultRepository extends PagingAndSortingRepository<InjectionResult, String> {

	@Query("SELECT inr FROM InjectionResult inr INNER JOIN inr.vaccine v INNER JOIN inr.prevention p WHERE v.vaccineName LIKE %?1%"
			+ " OR p.preventionName LIKE %?1% "
			+ " AND inr.status =1 order by inr.injectionResultId DESC")
	public Page<InjectionResult> listResults(String keyword, Pageable pageable);
	
	@Query("Select rs From InjectionResult rs WHERE rs.status =1 order by rs.injectionResultId DESC")
	public Page<InjectionResult> listResultsNoSearch( Pageable pageable);
	
	@Transactional
	@Query("SELECT r FROM InjectionResult r WHERE r.status=1 order by r.injectionResultId DESC")
	Iterable<InjectionResult> allResultByStatus();
	
	@Query(value = "SELECT prevention FROM injection_result", nativeQuery = true)
	List<String> findPreventions();
	
	@Query(value = "SELECT injection_place FROM injection_result", nativeQuery = true)
	List<String> findPlaces();
	
	@Transactional
    @Modifying
	@Query("Update InjectionResult i Set i.status = 0 Where i.injectionResultId = :id")
	void deleResultByStatus( @Param("id")String id);
	
	@Query(value = "SELECT i FROM InjectionResult i WHERE i.status=1 order by i.injectionResultId DESC")
	Iterable<InjectionResult> allResulByStatus ();
}
