package com.edu.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.edu.exception.CustomAccessDeniedHandler;

@Configuration
public class BeanConfig {
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AccessDeniedHandler getAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
}
