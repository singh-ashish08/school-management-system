package com.mvm.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.mvm.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	@Mock
	private StudentController studentController;

	@InjectMocks
	private StudentService studentService;
	
	@Test
	public void testAddStudent() {
		
	}
}
