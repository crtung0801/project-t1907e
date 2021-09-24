package com.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineTypeDto {

	private String id;

	private String name;
	
	private String description;

	private boolean status;
	
	private String image;
		
	public VaccineTypeDto(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
