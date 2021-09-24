package com.edu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePojo {

	private String id;
	
	private String email;
	
	@JsonProperty("verified_email")
	private boolean verifiedEmail;
	
	private String name;
	
	@JsonProperty("given_name")
	private String givenName;
	
	@JsonProperty("family_name")
	private String familyName;
	
	private String link;
	
	private String picture;
}
