package com.edu.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.edu.dto.UploadDto;
import com.edu.dto.VaccineTypeDto;
import com.edu.dto.VaccineTypeIdNameDto;
import com.edu.model.VaccineType;

public interface VaccineTypeService {

	Page<VaccineTypeDto> listVaccineTypes(int pageNumber, String keyword);

	List<VaccineTypeIdNameDto> getAllVaccineType();

	boolean saveVaccineType(VaccineType vaccineType);

	void deleteVaccineTypeById(String id);

	List<String> idVc();
	
	//
	Page<VaccineTypeDto> getPages(int pageNumber, String keyword);
	
	List<VaccineTypeDto> findAllByIdAndName();
	
	VaccineType findById(String id);
	
	void makeInActive(String id);
	
	void save(VaccineType vaccineType, UploadDto uploadDto, boolean isCreate) throws Exception;
	
	boolean existsById(String id);
}
