package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.model.Vaccine;

@Repository
public interface ReportChartVaccineRepository extends JpaRepository<Vaccine, String>{
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m1 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=1 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM1 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m2 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=2 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM2 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m3 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=3 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM3 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m4 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=4 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM4 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m5 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=5 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM5 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m6 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=6 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM6 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m7 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=7 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM7 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m8 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=8 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM8 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m9 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=9 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM9 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m10 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=10 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM10 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m11 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=11 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM11 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
	@Query (value = "SELECT sum(r.number_of_injection) AS total_injection_m12 FROM  vaccine v "
			+ "INNER JOIN injection_result r ON r.vaccine_id = v.vaccine_id "
			+ "INNER JOIN vaccine_type vt ON vt.vaccine_type_id = v.vaccine_type_id "
			+ "WHERE r.status=1 AND MONTH(r.injection_date)=12 AND YEAR(r.injection_date)=:year AND vt.vaccine_type_name = :typeName", nativeQuery = true)
	Integer numberInjectM12 (@Param("year") Integer year ,@Param("typeName") String typeName);
	
}