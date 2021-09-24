package com.edu.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="Place")
public class Place {
	@Id
	private int id;
	
	private String placeName;
	
	@OneToMany(mappedBy = "place")
	private List<InjectionResult> injections;
}
