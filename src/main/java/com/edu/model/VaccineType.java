package com.edu.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.edu.validation.annotation.CustomVaccineTypeNameContraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[VACCINE_TYPE]")
public class VaccineType {

	@Id
	@Column(name = "VACCINE_TYPE_ID", columnDefinition = "VARCHAR(50)", nullable = false)
//	@CustomVaccineTypeIdContraint
	@NotBlank(message = "vaccine type id must not empty!")
	private String vaccineTypeId;

	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(200)")
	private String description;

	@Column(name = "VACCINE_TYPE_NAME", columnDefinition = "VARCHAR(50)", nullable = false)
	@CustomVaccineTypeNameContraint
	private String vaccineTypeName;

	@Column(name = "IMAGE", columnDefinition = "VARCHAR(500)")
	private String image;
	
	@Column(name="STATUS", columnDefinition = "BIT DEFAULT 1" )
	private boolean status;

	@OneToMany(mappedBy = "vaccineType")
	@ToString.Exclude
	private List<Vaccine> vaccines;
	
	public VaccineType(boolean status) {
		this.status = status;
	}
	
}
