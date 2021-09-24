package com.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.edu.dto.ScheduleDto;
import com.edu.model.InjectionSchedule;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<InjectionSchedule, String> {
	@Query("SELECT new com.edu.dto.ScheduleDto(s.id , v.vaccineName, s.startDate, s.endDate, s.place, s.status, s.note) "
			+ "FROM InjectionSchedule s INNER JOIN s.vaccine v"
			+ "	Where v.active = true AND CONCAT(v.vaccineName, s.place, s.status, s.note)" + "	LIKE %?1%")
	public Page<ScheduleDto> list(String keyword, Pageable pageable);

	@Query("SELECT new com.edu.dto.ScheduleDto(s.id , v.vaccineName, s.startDate, s.endDate, s.place, s.status, s.note) "
			+ "FROM InjectionSchedule s INNER JOIN s.vaccine v WHERE v.active = true")
	public Page<ScheduleDto> findAllScheduleDto(Pageable pageable);

//	@Query("SELECT new com.edu.dto.ScheduleDto(s.id) FROM InjectionSchedule s WHERE s.id = :id")
//	public Optional<ScheduleDto> findOneById(@Param("id") String id);
}
