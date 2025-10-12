package com.mvm.controller;

import com.mvm.exception.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.stream.Collectors;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex,
																 HttpServletRequest request) {

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiResponse.setError("Bad Request");

		// Build a concise message listing field -> error
		String fieldErrors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
				.collect(Collectors.joining("; "));

		apiResponse.setMessage("Validation failed: " + fieldErrors);
		apiResponse.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
																	HttpServletRequest request) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiResponse.setError("Bad Request");
		String cause = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
		apiResponse.setMessage("Malformed JSON request: " + (cause == null ? "" : cause));
		apiResponse.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse> handleDuplicateResourceException(
			DuplicateResourceException ex,
			HttpServletRequest request) {

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpStatus.CONFLICT.value());
		apiResponse.setError("Conflict");
		apiResponse.setMessage(ex.getMessage());
		apiResponse.setPath(request.getRequestURI());

		return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
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
