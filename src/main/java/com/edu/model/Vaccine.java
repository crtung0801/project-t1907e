package com.edu.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[VACCINE]")
@Builder
public class Vaccine {

	@Id
	@NotBlank(message = "Vaccine Id not null")
	@Length(min = 8, message = "Length of Vaccine Id great or equals 10")
	@Column(name = "VACCINE_ID", columnDefinition = "VARCHAR(36)")
	private String vaccineId;

	@Column(name = "CONTRAINDICATION", columnDefinition = "VARCHAR(200)")
	private String contraindication;

	@Column(name = "INDICATION", columnDefinition = "VARCHAR(200)")
	private String indication;

	@Max(value = 15, message = "Value number of injection is less or equal 15")
	@Column(name = "NUMBER_OF_INJECTION", length = 15)
	private int numberOfInjection;

	@Column(name = "ORIGIN", columnDefinition = "VARCHAR(50)")
	private String origin;

	@Column(name = "TIME_BEGIN_NEXT_INJECTION", columnDefinition = "DATE")
	private String timeBeginNextInjection;

	@Column(name = "TIME_END_NEXT_INJECTION", columnDefinition = "DATE")
	private String timeEndNextInjection;

	@Column(name = "USAGE", columnDefinition = "VARCHAR(200)")
	private String usage;

	@NotNull(message = "Vaccine Name not null")
	@Length(min = 5, message = "Length Vaccine Name greate or equals 5")
	@Column(name = "VACCINE_NAME", columnDefinition = "VARCHAR(100)", nullable = false)
	private String vaccineName;

	@Column(name = "IS_ACTIVE", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private boolean active;

	@NotNull(message = "Vaccine Type Not Null")
	@ManyToOne
	@JoinColumn(name = "VACCINE_TYPE_ID", nullable = false)
	@ToString.Exclude
	private VaccineType vaccineType;

	@OneToMany(mappedBy = "vaccine")
	private List<InjectionSchedule> injectionSchedules;

	@OneToMany(mappedBy = "vaccine")
	@ToString.Exclude
	private List<InjectionResult> injectionResults;

	@Transient
	private String fileType;

	@Transient
	private MultipartFile file;

	public Vaccine(boolean active) {
		this.active = active;
	}
}
