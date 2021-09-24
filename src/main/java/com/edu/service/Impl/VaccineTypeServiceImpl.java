package com.edu.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.dto.UploadDto;
import com.edu.dto.VaccineTypeDto;
import com.edu.dto.VaccineTypeIdNameDto;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.ResourceNotFoundException;
import com.edu.model.Employee;
import com.edu.model.VaccineType;
import com.edu.repository.VaccineTypeRepository;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VaccineTypeServiceImpl implements VaccineTypeService {

	@Autowired
	private VaccineTypeRepository vaccineTypeRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${avatar.base.folder}")
	private String uploadFolder;

	@Override
	public boolean saveVaccineType(VaccineType vaccineType) {
		return vaccineTypeRepository.save(vaccineType) != null;
	}

	@Override
	public void deleteVaccineTypeById(String id) {
		vaccineTypeRepository.deleteById(id);
	}

	@Override
	public Page<VaccineTypeDto> listVaccineTypes(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.NUMBER_PER_PAGE);
		if (keyword != null) {
			return vaccineTypeRepository.listVaccineTypes(keyword, pageable)
					.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class));
		}
		return vaccineTypeRepository.findAllByOrderByVaccineTypeIdDesc(pageable)
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class));
	}

	@Override
	public List<VaccineTypeIdNameDto> getAllVaccineType() {
		return vaccineTypeRepository.findAll().stream()
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeIdNameDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> idVc() {
		return vaccineTypeRepository.idVc();
	}

	/////////////////////////////////////
	@Override
	public Page<VaccineTypeDto> getPages(int pageNumber, String keyword) {

		Pageable pageable = PageRequest.of(pageNumber - 1, 5);

		if (keyword == null || keyword.isBlank()) {
			return vaccineTypeRepository.getPages(pageable);
		}

		return vaccineTypeRepository.getPages(keyword, pageable);
	}
	
	@Override
	public List<VaccineTypeDto> findAllByIdAndName() {
		return vaccineTypeRepository.findAllByIdAndName();
	}

	@Override
	public VaccineType findById(String id) {
		Optional<VaccineType> optional = vaccineTypeRepository.findById(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException(Constant.NOT_FOUND_VACCINE_TYPE_ID);
		}

		return optional.get();
	}

	@Override
	public void makeInActive(String id) {
		VaccineType vaccineType = findById(id);
		vaccineType.setStatus(false);
		vaccineTypeRepository.save(vaccineType);
	}

	@Override
	public void save(VaccineType vaccineType, UploadDto uploadDto, boolean isCreate) throws IOException, Exception{
		MultipartFile file = uploadDto.getFile();

		if (vaccineType == null) {
			throw new IllegalArgumentException(Constant.NULL_VACCINE_TYPE);
		}

		if (file != null && !file.isEmpty()) {

			boolean matches = file.getOriginalFilename().matches(Constant.REGEX_IMAGE);
			if (!matches) {
				throw new HandlerFileException(Constant.INVALID_FILE_IMAGE);
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFolder + vaccineType.getVaccineTypeId() + vaccineType.getVaccineTypeName() + ".jpg");
			Files.write(path, bytes);
			vaccineType.setImage(vaccineType.getVaccineTypeId() + vaccineType.getVaccineTypeName() + ".jpg");
			
		}

		if (isCreate) {
			if (vaccineTypeRepository.existsById(vaccineType.getVaccineTypeId())) {
				throw new HandlerIdException(Constant.EXIST_ID);
			}
		}
		
		vaccineTypeRepository.save(vaccineType);
	}

	@Override
	public boolean existsById(String id) {
		return vaccineTypeRepository.existsById(id);
	}
}
