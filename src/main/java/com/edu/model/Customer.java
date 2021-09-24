package com.edu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "[CUSTOMER]")
public class Customer {

	@Id
	@GeneratedValue(generator = "customer")
	@GenericGenerator(name = "customer", strategy = "com.edu.generator.Customer")
	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;

	@NotBlank(message = "Enter your Address")
	@Column(name = "ADDRESS", nullable = false, columnDefinition = "VARCHAR(255)")
	private String address;

	@NotNull(message = "Enter your birthday")
	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@Email(message = "Email format is not correct")
	@NotBlank(message = "Enter your email")
	@Column(name = "EMAIL", nullable = false, columnDefinition = "VARCHAR(100)")
	private String email;

	@NotBlank(message = "Enter your fullname")
	@Length(max = 50, message = "Length of fullname <=50")
	@Column(name = "FULL_NAME", nullable = false, columnDefinition = "VARCHAR(100)")
	private String fullName;

	@Column(name = "GENDER", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int gender;

	@NotBlank(message = "Enter your Identity Card")
	@Length(max = 10, message = "Length of identity Card =10")
	@Column(name = "IDENTITY_CARD", nullable = false, columnDefinition = "VARCHAR(12)")
	private String identityCard;

	@NotBlank(message = "Enter your password")
	@Length(min = 6, message = "Length of password more 6 or little  20")
	@Column(name = "PASSWORD", nullable = false, columnDefinition = "VARCHAR(255)")
	private String password;

	@NotBlank(message = "Enter your phone")
	@Length(min = 10, max = 11, message = "Length of phone little or equals 11")
	@Column(name = "PHONE", nullable = false, columnDefinition = "VARCHAR(20)")
	private String phone;

	@NotBlank(message = "Enter your username")
	@Length(max = 10, message = "Length of username = 10")
	@Column(name = "USERNAME", nullable = false, columnDefinition = "VARCHAR(255)")
	private String username;

	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;
	@NotBlank(message = "Captcha not null")
	@Transient
	private String captcha;

	@NotBlank(message = "Enter your Re-password")
	@Transient
	private String confirmPassword;

	@OneToMany(mappedBy = "customer")
	private List<InjectionResult> injectionResults;
}
