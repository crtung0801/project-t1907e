package com.edu.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.model.Place;
import com.edu.model.Prevention;
import com.edu.repository.PlaceRepository;
import com.edu.repository.PreventionRepository;
import com.edu.service.PlaceAndPrevention;

@Service
public class PlaceAndPreventionServiceImpl  implements PlaceAndPrevention{

	@Autowired
	private PreventionRepository preRepo;
	
	@Autowired
	private PlaceRepository placeRepo;
	
	
	public List<Place> places(){
		return placeRepo.findAll();
	}
	
	public List<Prevention> preventions(){
		return  preRepo.findAll();
	}
}
