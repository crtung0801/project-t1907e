package com.edu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.edu.model.Vaccine;

public interface ReadFileService {
	
	List<Vaccine> findAll();

	boolean save(MultipartFile file) throws Exception;
}
