package com.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "INJECTION_SCHEDULE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InjectionSchedule {

	@Id
	@Column(name = "INJECTION_SCHEDULE_ID", columnDefinition = "VARCHAR(100)")
	private String id;

	@Column(name = "NOTE", columnDefinition = "VARCHAR(200)")
	@Size(max = 200, message = "Note must be less 200 character!")
	private String note;

	@NotBlank(message = "Place not empty!")
	@Size(max = 100, message = "Place must be less 100 character!")
	@Column(name = "PLACE", columnDefinition = "VARCHAR(100)", nullable = false)
	private String place;

	@Column(name = "START_DATE", columnDefinition = "DATE", nullable = false)
	@NotBlank(message = "Start date not empty!")
	private String startDate;

	@NotNull
	@Column(name = "END_DATE", columnDefinition = "DATE", nullable = false)
	@NotBlank(message = "End date not empty!")
	private String endDate;

	@Column(name = "STATUS", columnDefinition = "VARCHAR(50)", nullable = false)
	@Size(max = 50)
	@NotNull(message = "Status is madatory")
	private String status;

	@ManyToOne
	@JoinColumn(name = "VACCINE_ID")
	@ToString.Exclude
	@NotNull(message = "Vaccine must be madatory!")
	private Vaccine vaccine;
}
