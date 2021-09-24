package com.edu.service.Impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.dto.VaccineDto;
import com.edu.dto.VaccineDto1;
import com.edu.dto.VaccineTypeDto;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.HandlerNameException;
import com.edu.exception.ResourceNotFoundException;
import com.edu.model.Vaccine;
import com.edu.model.VaccineType;
import com.edu.repository.VaccineRepository;
import com.edu.service.VaccineService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;

@Service
public class VaccineServiceImpl implements VaccineService {

	@Autowired
	private VaccineRepository vaccineRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Vaccine> findAll() {
		return vaccineRepository.findAll();
	}

	@Override
	public List<Vaccine> search(String value) {
		return vaccineRepository.search(value);
	}

	@Override
	public void saveVaccine(Vaccine vaccine) {
		vaccineRepository.save(vaccine);
	}

	public boolean findId(String id) {
		return vaccineRepository.findById(id).isPresent();
	}

	@Override
	public List<Object[]> findVaccines() {
		return vaccineRepository.findVaccines();
	}

	@Override
	public Optional<Vaccine> findOneByVaccineName(String vaccineName) {
		return vaccineRepository.findOneByVaccineName(vaccineName);
	}

	@Override
	public List<Vaccine> findByVaccineTypeId(String vaccineTypeId) {
		return vaccineRepository.findByVaccineTypeId(vaccineTypeId);
	}

	@Override
	public Page<VaccineDto> listVaccines(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.NUMBER_PER_PAGE);
		if (keyword != null) {
			return vaccineRepository.listVaccines(keyword, pageable)
					.map(vaccine -> modelMapper.map(vaccine, VaccineDto.class));
		}
		return vaccineRepository.findAllByOrderByVaccineIdDesc(pageable)
				.map(vaccine -> modelMapper.map(vaccine, VaccineDto.class));
	}

	/////////////////////////////////

	@Override
	public Page<VaccineDto1> getPages(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);

		if (keyword == null || keyword.isBlank()) {
			return vaccineRepository.getPages(pageable);
		}

		return vaccineRepository.getPages(keyword, pageable);
	}

	public void makeInActive(String id) {
		Vaccine vaccine = findById(id);
		vaccine.setActive(false);
		vaccineRepository.save(vaccine);
	}

	@Override
	public Vaccine findById(String id) {
		Optional<Vaccine> optional = vaccineRepository.findById(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException(Constant.VACCINE_NOT_FOUND + id);
		}

		return optional.get();
	}

	@Override
	public void save(Vaccine vaccine, boolean isCreate) throws Exception {
		String startDate = null;
		String endDate = null;

		if (vaccine == null) {
			throw new IllegalArgumentException(Constant.NULL_VACCINE_TYPE);
		}

		startDate = vaccine.getTimeBeginNextInjection();
		endDate = vaccine.getTimeEndNextInjection();

		if (!startDate.isBlank() && !endDate.isBlank()
				&& !Utils.compare(LocalDate.parse(startDate), LocalDate.parse(endDate))) {
			throw new HandlerDateException(Constant.INVALID_DATE);
		}

		if (isCreate) {
			if (vaccineRepository.existsById(vaccine.getVaccineId())) {
				throw new HandlerIdException(Constant.EXIST_ID);
			}
		}

		vaccineRepository.save(vaccine);
	}

	@Override
	public boolean existsById(String id) {
		return vaccineRepository.existsById(id);
	}

	@Override
	public void save(Vaccine vaccine) throws Exception {

		if (vaccine == null) {
			throw new IllegalArgumentException(Constant.NULL_VACCINE_TYPE);
		}

		if (vaccine.getVaccineId() == null || vaccine.getVaccineId().isBlank()) {
			throw new HandlerIdException(Constant.INVALID_VACCINE_ID);
		}

		if (vaccine.getVaccineName() == null || vaccine.getVaccineName().isEmpty()) {
			throw new HandlerNameException(Constant.INVALID_VACCINE_NAME);
		}

		if (vaccine.getVaccineType().getVaccineTypeId() == null
				|| vaccine.getVaccineType().getVaccineTypeId().isBlank()) {
			throw new HandlerIdException(Constant.NOT_FOUND_VACCINE_TYPE_ID);
		}

		if (vaccine.getTimeBeginNextInjection() != null && vaccine.getTimeEndNextInjection() != null
				&& !Utils.compare(LocalDate.parse(vaccine.getTimeBeginNextInjection()),
						LocalDate.parse(vaccine.getTimeEndNextInjection()))) {
			throw new HandlerDateException(Constant.INVALID_DATE);
		}

		vaccineRepository.save(vaccine);

	}

	@Override
	public List<VaccineDto1> findAllByIdAndName() {
		return vaccineRepository.findAllByIdAndName();
	}

}
