package com.edu.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "[NEWS_TYPE]")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class NewsType {

	@Id
	@Column(name = "[NEWS_TYPE_ID]", nullable = false, columnDefinition = "VARCHAR(36)")
	private String newsTypeId;

	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(10)")
	private String description;

	@Column(name = "[NEW_TYPE_NAME]", columnDefinition = "VARCHAR(50)")
	private String newsTypeName;

	@OneToMany(mappedBy = "newsType")
	private List<News> news;
}
