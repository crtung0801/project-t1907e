package com.edu.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class UploadDto {
	private MultipartFile file;
}
