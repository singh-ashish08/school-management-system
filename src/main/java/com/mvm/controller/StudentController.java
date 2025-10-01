package com.mvm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvm.dto.StudentCreateDto;
import com.mvm.dto.StudentDto;
import com.mvm.dto.StudentResponseDto;
import com.mvm.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	// Student-related endpoints will be defined here

	@Autowired
	private StudentService studentService;

	@PostMapping(value = "/students/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentCreateDto> addStudent(@Valid @RequestBody StudentCreateDto studentCreateDto) {

		// log to verify binding
		System.out.println(">>> DTO = " + studentCreateDto);

		StudentCreateDto saved = studentService.save(studentCreateDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/fullDetails")
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		List<StudentDto> students = studentService.getAllStudents();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("/lessDetails/{id}")
	public ResponseEntity<StudentResponseDto> getResponseById(Long id) {
		StudentResponseDto studentRDto = studentService.getResponseById(id);
		return ResponseEntity.ok(studentRDto);
	}

	@GetMapping("/lessDetails/all")
	public ResponseEntity<List<StudentResponseDto>> getAllResponse() {
		List<StudentResponseDto> students = studentService.getAllResponse();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("/fullDetailsBy/{id}")
	public ResponseEntity<StudentDto> getDtoById(Long id) {
		StudentDto studentDto = studentService.getDtoById(id);
		return ResponseEntity.ok(studentDto);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(long id) {
		studentService.delete(id);
		return ResponseEntity.ok("Student record deleted successfully");
	}

}
