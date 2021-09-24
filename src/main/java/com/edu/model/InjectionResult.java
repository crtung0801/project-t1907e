package com.edu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[INJECTION_RESULT]")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InjectionResult {

	@Id
	@GeneratedValue(generator = "result")
	@GenericGenerator(name = "result", strategy = "com.edu.generator.InjectionResult")
	@Column(name = "INJECTION_RESULT_ID", columnDefinition = "VARCHAR(255)")
	private String injectionResultId;

	@Column(name = "INJECTION_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date injectionDate;

	@Column(name = "NEXT_INJECTION_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nextInjectionDate;

	@Column(name = "NUMBER_OF_INJECTION")
	private int numberOfInjection;

	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;

	@NotNull(message = "Vaccine Type  Not Null")
	@ManyToOne
	@JoinColumn(name = "VACCINE_ID", nullable = false)
	private Vaccine vaccine;

	@NotNull(message = "Customer  Not Null")
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private Customer customer;

	@NotNull(message = "Prevention Not Null")
	@ManyToOne
	@JoinColumn(name = "PREVENTION", nullable = false)
	private Prevention prevention;

	@ManyToOne
	@JoinColumn(name = "INJECTION_PLACE")
	private Place place;

}
