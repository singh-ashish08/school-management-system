package com.mvm.controller;

//Mockito static helpers
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

//MockMvc static helpers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvm.dto.StudentCreateDto;
import com.mvm.service.StudentService;

@WebMvcTest(StudentController.class)
//@AutoConfigureMockMvc(addFilters = false)   // <- disables Spring Security filters for this test
public class StudentControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	StudentService studentService;

	private static final String URL = "/api/students/students/add";

	// ⚠️ Helper: build a VALID DTO for your project.
	// TODO: set the fields that are actually required in your StudentCreateDto.
	private StudentCreateDto validDto;

	@BeforeEach
	void setUp() {
		// Build a DTO with all required fields populated
		validDto = new StudentCreateDto();
		validDto.setName("John Doe");
		validDto.setEmail("john.doe@example.com");
		validDto.setAddress("123 Main St");
		validDto.setPhoneNumber("1234567890");
		validDto.setCourse("Math");
		validDto.setEnrollmentDate(LocalDate.of(2024, 9, 1));
		validDto.setStatus(true);
		validDto.setGuardianName("Jane Doe");
		validDto.setGuardianContact("0987654321");
		validDto.setDateOfBirth(LocalDate.of(2010, 1, 15));
		validDto.setClassSection("A");
		validDto.setRollNumber(12);
		validDto.setBloodGroup("B+");
		validDto.setEmergencyContact("112");
	}

	@Test
	@DisplayName("POST /api/students/students/add returns 201 Created with saved DTO")
	void addStudent_Returns201Created() throws Exception {
		// Arrange
		when(studentService.save(any(StudentCreateDto.class))).thenReturn(validDto);

		// Act + Assert
		mockMvc.perform(post(URL).with(httpBasic("deepa","ashish")).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validDto))).andExpect(status().isCreated())
				// .andExpect(content.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.status").value(true));

		// Verify the service is called with correct DTO
		ArgumentCaptor<StudentCreateDto> captor = ArgumentCaptor.forClass(StudentCreateDto.class);
		verify(studentService, times(1)).save(captor.capture());
	}

}
