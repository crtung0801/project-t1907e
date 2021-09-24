package com.edu.dto;

import java.io.Serializable;
import java.util.List;

import com.edu.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private String role;
	
//	public Account (String username, String password) {
//		this.username = username;
//		this.password = password;
//	}
}
