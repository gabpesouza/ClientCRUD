package com.devsuperior.clientcrud.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.clientcrud.services.exceptions.DataBaseException;
import com.devsuperior.clientcrud.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(HttpServletRequest request, ResourceNotFoundException e){
		StandardError se = new StandardError();
		se.setError("Resource not found");
		se.setMessage(e.getMessage());
		se.setStatus(HttpStatus.NOT_FOUND.value());
		se.setTimestamp(Instant.now());
		se.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> DataBaseViolation(HttpServletRequest request, DataBaseException e){
		StandardError se = new StandardError();
		se.setError("Database integrity violation");
		se.setMessage(e.getMessage());
		se.setStatus(HttpStatus.BAD_REQUEST.value());
		se.setTimestamp(Instant.now());
		se.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
	}

}
