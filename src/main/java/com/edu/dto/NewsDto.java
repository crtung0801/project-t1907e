package com.edu.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NewsDto {
	
	private Long newsId;
	private String content;
	private String title;
	private int status;
	private String datePost;
	
	
	
}
