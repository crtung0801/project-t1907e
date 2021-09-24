package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.model.NewsType;

public interface NewsTypeRepository  extends JpaRepository<NewsType, String>{

}
