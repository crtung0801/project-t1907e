package com.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDto1 {

	private String id;

	private String name;

	private String vaccineTypeName;

	private int numberOfInjection;

	private String origin;

	private boolean active;

	public VaccineDto1(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
