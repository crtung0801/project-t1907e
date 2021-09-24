package com.edu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.model.Vaccine;

@Repository
public interface ReadFileReposiroty extends CrudRepository<Vaccine, String> {

}
