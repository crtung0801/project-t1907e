package com.edu.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.edu.validation.annotation.CustomBirthDateConstraint;
import com.edu.validation.annotation.CustomEmailConstraint;
import com.edu.validation.annotation.CustomPhoneConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "[EMPLOYEE]")
public class Employee {

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "VARCHAR(36)", unique = true)
	@NotBlank (message = "Employee ID not Empty")
	@NotNull (message = "Employee ID not Null")
	@Size (max = 36, message = "The lenght <= 36 " )
	private String employeeId;

	@Column(name = "EMPLOYEE_NAME", nullable = false, columnDefinition = "VARCHAR(255)")
	@NotBlank (message = "Employee name not Empty")
	@NotNull (message = "Full Name not Null")
	@Size (max = 255, message = "The lenght <= 255 " )
	private String fullName;

	@Column(name = "EMAIL", nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
	@Size (max = 100, message = "The lenght <= 100 " )
	@CustomEmailConstraint
	private String email;

	@Column(name = "ADDRESS", nullable = false, columnDefinition = "VARCHAR(255)")
	@NotNull (message = "Address not Null")
	@NotEmpty (message = "Address not Empty")
	@Size (max = 255, message = "The lenght <= 255 " )
	private String address;

	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATE", nullable = false)
	@CustomBirthDateConstraint
	private String birthDate;

	@Column(name = "GENDER", nullable = false, columnDefinition = "VARCHAR(10)")
	@NotNull (message = "Please Select Gender !")
	private String gender;

	@Column(name = "IMAGE", columnDefinition = "VARCHAR(255)")
	private String image;

	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(255)")
	private String password;

	@Column(name = "PHONE", nullable = false, columnDefinition = "VARCHAR(20)", unique = true)
	@CustomPhoneConstraint
	private String phone;

	@Column(name = "POSITION", columnDefinition = "VARCHAR(100)")
	private String position;

	@Column(name = "USERNAME", columnDefinition = "VARCHAR(255)")
	private String username;

	@Column(name = "WORKING_PLACE", nullable = false, columnDefinition = "VARCHAR(255)")
	private String workingPlace;

	@Column(nullable = false)
	@Min(value = 1, message = "Please Select Actor !")
	private int idOfRole;

	@Column(name="STATUS",nullable = false)
	private int status;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EMPLOYEE_ROLE", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;
}
