package com.edu.service;

import org.springframework.data.domain.Page;

import com.edu.dto.ScheduleDto;
import com.edu.model.InjectionSchedule;
import com.edu.model.Vaccine;

public interface ScheduleService {

	InjectionSchedule findById(String id);

	// ScheduleDto findOneById(String id);

	Page<ScheduleDto> list(int pageNumber, String keyword);

	void save(InjectionSchedule schedule, boolean isCreate) throws Exception;
}
