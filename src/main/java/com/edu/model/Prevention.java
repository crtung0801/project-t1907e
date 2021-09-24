package com.edu.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Prevention")
public class Prevention {
	@Id
	private int id;

	private String preventionName;
	
	@OneToMany(mappedBy = "prevention")
	private List<InjectionResult> injections;
}
