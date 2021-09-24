package com.edu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.edu.dto.VaccineDto;
import com.edu.dto.VaccineDto1;
import com.edu.model.Vaccine;

public interface VaccineService {

	Page<VaccineDto> listVaccines(int pageNumber, String keyword);

	List<Vaccine> findAll();

	List<Vaccine> search(String value);

	void saveVaccine(Vaccine vaccine);

	List<Object[]> findVaccines();

	Optional<Vaccine> findOneByVaccineName(String vaccineName);

	List<Vaccine> findByVaccineTypeId(String vaccineTypeId);

	////////////////////////////////////////
	Page<VaccineDto1> getPages(int pageNumber, String keyword);

	void makeInActive(String id);

	Vaccine findById(String id);

	void save(Vaccine vaccine, boolean isCreate) throws Exception;

	void save(Vaccine vaccine) throws Exception;

	boolean existsById(String id);

	List<VaccineDto1> findAllByIdAndName();
}
