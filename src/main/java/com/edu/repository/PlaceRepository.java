package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

}
