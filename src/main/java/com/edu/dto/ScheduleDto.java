package com.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

	private String id;

	private String vaccineName;

	private String startDate;

	private String endDate;

	private String place;

	private String status;

	private String note;

	public ScheduleDto(String id) {
		this.id = id;
	}
}
