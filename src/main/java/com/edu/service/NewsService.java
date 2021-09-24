package com.edu.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.edu.dto.NewsDto;
import com.edu.model.News;

public interface NewsService {
	
	Page<NewsDto> listNews(int pageNumber,String keyword);
	
	void changeStatus (Long newsId);	
	
	News addNews (News news);

	Optional<News> findNewsById (Long newsId);
	
}
