package com.edu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.model.InjectionResult;
import com.edu.repository.ResultRepository;
import com.edu.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

	@Autowired
	private ResultRepository resultRepository;

	@Override
	public InjectionResult findById(String id) {
		return resultRepository.findById(id).get();
		
	}

	@Override
	public boolean save(InjectionResult injectionResult) {
		if (injectionResult != null) {
			resultRepository.save(injectionResult);
			return true;
		}
		return false;
	}

	@Override
	public Page<InjectionResult> listResults(int pageNUmber, String keyword) {
		Pageable pageable = PageRequest.of(pageNUmber - 1, 5);
		if (keyword != null) {
			return resultRepository.listResults(keyword, pageable);
		}
		return resultRepository.listResultsNoSearch(pageable);
	}

	@Override
	public Iterable<InjectionResult> allResultByStatus() {
		return resultRepository.allResultByStatus();
	}

	@Override
	public void changeStatus(String id) {
		Optional<InjectionResult> optionalResult = resultRepository.findById(id);
		InjectionResult injectionResult = null;
		if (optionalResult.isPresent()) {
			injectionResult = optionalResult.get();
			injectionResult.setStatus(0);
			resultRepository.save(injectionResult);
		}
	}

	@Override
	public List<String> findPreventions() {
		return resultRepository.findPreventions();
	}

	@Override
	public List<String> findPlaces() {
		return resultRepository.findPlaces();
	}
}
