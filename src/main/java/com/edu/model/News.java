package com.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[NEWS]")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

	@Id
	@Column(name = "[NEWS_ID]")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newsId;

	@Column(name = "CONTENT", columnDefinition = "VARCHAR(4000)", nullable = false)
	@NotNull (message = "Content not Null")
	@NotBlank (message = "Content not Empty")
	@Size (max=4000, message = "Lenght =< 4000 digit")
	private String content;

	@Column(name = "PREVIEW", columnDefinition = "VARCHAR(1000)")
	@NotNull (message = "Preview not Null")
	@NotBlank (message = "Preview not Empty")
	@Size (max=1000, message = "Lenght =< 1000 digit")
	private String preview;

	@Column(name = "TITLE", columnDefinition = "VARCHAR(300)", nullable = false)
	@NotNull (message = "Title not Null")
	@NotBlank (message = "Title not Empty")
	@Size (max=300, message = "Lenght =< 300 digit")
	private String title;

	@Column(name = "DATE_POST", columnDefinition = "DATE")
	private String datePost;

	@Column(name = "STATUS")
	private int status;

	@ManyToOne
	@JoinColumn(name = "NEWS_TYPE_ID")
	private NewsType newsType;
}
