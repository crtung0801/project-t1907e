package com.edu.service;

import java.util.List;

import com.edu.model.Place;
import com.edu.model.Prevention;

public interface PlaceAndPrevention {
	List<Place> places();
	
	List<Prevention> preventions();

}
