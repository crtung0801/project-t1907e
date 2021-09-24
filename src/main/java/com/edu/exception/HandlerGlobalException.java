package com.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerGlobalException {
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handlerResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorObject eObject = ErrorObject.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorObject>(eObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handlerNoDataFoundException(NoDataFoundException ex) {
		ErrorObject eObject = ErrorObject.builder()
				.status(HttpStatus.NO_CONTENT.value())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorObject>(eObject, HttpStatus.OK);
	}
}
