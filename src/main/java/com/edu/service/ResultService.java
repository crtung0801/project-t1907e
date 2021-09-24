package com.edu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.edu.model.InjectionResult;

public interface ResultService {
	
	List<String> findPreventions();
	
	List<String> findPlaces();
	
	InjectionResult findById(String id);
	
	boolean save (InjectionResult injectionResult);
	
	Page<InjectionResult> listResults(int pageNumber,String keyword);
	
	
	Iterable<InjectionResult> allResultByStatus ();
	
	void changeStatus (String id);
}
