package com.mvm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	
	@GetMapping("/csrftoken")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
		
	}
	@GetMapping("/hello")
	public ResponseEntity<String> sayHello(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		String message = "Hello, welcome to Maharishi Vidya Mandir School, "+"SessionId = "+sessionId;
		return new ResponseEntity(message,HttpStatus.OK);
	}

	@PostMapping("/test")
	public ResponseEntity<String> test(@RequestBody String body) {
		log.info("RAW BODY: {}", body);
		return new ResponseEntity(body,HttpStatus.OK);
	}

}
