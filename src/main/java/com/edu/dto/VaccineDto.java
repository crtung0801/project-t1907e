package com.edu.dto;

import lombok.Data;

@Data
public class VaccineDto {
	
	private String vaccineId;

	private String origin;

	private String vaccineName;
	
	private boolean active;
	
	private VaccineTypeDto vaccineType;
	

}
