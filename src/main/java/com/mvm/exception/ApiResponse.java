package com.mvm.exception;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {

	private Instant timestamp = Instant.now();
	private int status; // e.g. 400, 404, 500
	private String error; // e.g. "Bad Request"
	private String message; // your detailed message
	private String path; // request URI
	private List<String> errors; // optional: validation errors
}
