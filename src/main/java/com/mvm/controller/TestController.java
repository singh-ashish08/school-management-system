package com.mvm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/hello")
	public ResponseEntity<String> sayHello() {
		String message = "Hello, welcome to Maharishi Vidya Mandir School ";
		return ResponseEntity.ok(message);
	}

	@PostMapping("/test")
	public void test(@RequestBody String body) {
		System.out.println("RAW BODY: " + body);
	}

}
