package com.edu.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.dto.ScheduleDto;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.ResourceNotFoundException;
import com.edu.model.InjectionSchedule;
import com.edu.model.Vaccine;
import com.edu.repository.ScheduleRepository;
import com.edu.service.ScheduleService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	public InjectionSchedule findById(String id) {

		Optional<InjectionSchedule> optional = scheduleRepository.findById(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException(Constant.SCHEDULE_NOT_FOUND + id);
		}

		return optional.get();
	}

	@Override
	public Page<ScheduleDto> list(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (keyword != null) {
			return scheduleRepository.list(keyword, pageable);
		}

		return scheduleRepository.findAllScheduleDto(pageable);
	}

	@Override
	public void save(InjectionSchedule schedule, boolean isCreate) throws Exception {
		String startDate = null;
		String endDate = null;

		if (schedule == null) {
			throw new IllegalArgumentException(Constant.NULL_SCHEDULE);
		}

		startDate = schedule.getStartDate();
		endDate = schedule.getEndDate();

		if (!startDate.isBlank() && !endDate.isBlank()
				&& !Utils.compare(LocalDate.parse(startDate), LocalDate.parse(endDate))) {
			throw new HandlerDateException(Constant.INVALID_DATE);
		}

		if (isCreate) {
			if (scheduleRepository.existsById(schedule.getId())) {
				throw new HandlerIdException(Constant.EXIST_ID);
			}
			schedule.setId(LocalDateTime.now() + "");

		}

		scheduleRepository.save(schedule);

	}
}
