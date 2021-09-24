package com.edu.service;

import java.util.List;
import java.util.Optional;

import com.edu.model.NewsType;

public interface NewsTypeService {
	List<NewsType> fillAll();
	
	Optional<NewsType> findById (String newsTypeId);
	
	
}
