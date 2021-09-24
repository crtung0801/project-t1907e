package com.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
	private String id;

	private String fullName;

	private String birthDate;
	
	private String gender;
	
	private String phone;
	
	private String address;

	private String image;

//	private int idOfRole;

//	private int status;
}
