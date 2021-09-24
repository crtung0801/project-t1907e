package com.edu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.model.NewsType;
import com.edu.repository.NewsTypeRepository;
import com.edu.service.NewsTypeService;
@Service
public class NewsTypeServiceImpl implements NewsTypeService {
	
	@Autowired
	private NewsTypeRepository newsTypeRepository;
	
	@Override
	public List<NewsType> fillAll() {
		return newsTypeRepository.findAll();
	}

	@Override
	public Optional<NewsType>  findById(String newsTypeId) {
		return newsTypeRepository.findById(newsTypeId);
	}
		
}
