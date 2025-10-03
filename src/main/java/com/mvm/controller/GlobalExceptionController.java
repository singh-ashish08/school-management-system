package com.mvm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mvm.exception.ApiResponse;
import com.mvm.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			HttpServletRequest request) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
		apiResponse.setError("Not Found");
		apiResponse.setMessage(ex.getMessage());
		apiResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiResponse.setError("Internal Server Error");
		apiResponse.setMessage(ex.getMessage());
		apiResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
