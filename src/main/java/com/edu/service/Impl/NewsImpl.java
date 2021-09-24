package com.edu.service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.dto.NewsDto;
import com.edu.model.News;
import com.edu.repository.NewsRepository;
import com.edu.service.NewsService;

@Service
public class NewsImpl implements NewsService {

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public Page<NewsDto> listNews(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (keyword != null) {
			return newsRepository.allNewsByStatus(keyword, pageable).map(news -> modelmapper.map(news, NewsDto.class));
		}
		return (Page<NewsDto>) newsRepository.allNewsByStatus(pageable).map(news -> modelmapper.map(news, NewsDto.class));
	}

	@Override
	public void changeStatus(Long newsId) {
		Optional<News> optionalNews = newsRepository.findById(newsId);
		News news = null;
		if (optionalNews.isPresent()) {
			news = optionalNews.get();
			news.setStatus(0);
			newsRepository.save(news);
		}
	}

	@Override
	public News addNews(News news) {
		return newsRepository.save(news);
	}

	@Override
	public Optional<News> findNewsById(Long newsId) {
		return newsRepository.findById(newsId);
	}
}
