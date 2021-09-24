package com.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCustomerDto {

	private String customerId;

	private String address;

	private String dateOfBirth;

	private String fullName;

	private String identityCard;

	private Integer numberOfInjection;

}
