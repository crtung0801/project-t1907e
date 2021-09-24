package com.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.edu.model.News;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
	@Query("Select n From News n " 
			+ "	Where CONCAT(n.title,n.content)"
			+ "	LIKE %?1%")
	public Page<News> allNewsByStatus(String keyword, Pageable pageable);
	
	@Query(value = "SELECT i FROM News i WHERE i.status=1")
	Page<News> allNewsByStatus (Pageable pageable);

}
