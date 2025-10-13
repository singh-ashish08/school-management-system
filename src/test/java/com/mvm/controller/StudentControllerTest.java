package com.mvm.controller;

//Mockito static helpers
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

//MockMvc static helpers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mvm.exception.DuplicateResourceException;
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
		mockMvc.perform(post(URL).with(httpBasic("deepa","ashish"))
				                 .with(csrf())
				                 .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				                 .content(objectMapper.writeValueAsString(validDto))).andExpect(status().isCreated())
				// .andExpect(content.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.status").value(true));

		// Verify the service is called with correct DTO
		ArgumentCaptor<StudentCreateDto> captor = ArgumentCaptor.forClass(StudentCreateDto.class);
		verify(studentService, times(1)).save(captor.capture());
	}

	@Test
	public void createStudent_whenMissingRequiredField_thenReturns400()throws Exception{
		String invalidJson = """
            {
              "email": "john.doe@example.com",
              "phoneNumber": "9876543210",
              "enrollmentDate": "2023-09-01",
              "status": true,
              "guardianName": "Mr Guardian",
              "dateOfBirth": "2005-06-15"
            }
            """;

		// Act & Assert: expect 400 Bad Request
		mockMvc.perform(post(URL)
						// add basic auth + csrf if your security requires them (you used them above)
						.with(httpBasic("deepa", "ashish"))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(invalidJson))
				.andExpect(status().isBadRequest());

		// Verify service NOT called because validation should fail before hitting the service.
		verify(studentService, times(0)).save(any(StudentCreateDto.class));
	}


	@Test
	public void createStudent_whenInvalidField_thenReturns400()throws Exception{
		String inValidJson= """
				 {
				 "name":"ashish",
				 "email": "john.doe@example.com",
              "phoneAumber": "9876543210",
              "enrollmentDate": "2023-09-01",
              "status": true,
              "guardianName": "Mr Guardian",
              "dateOfBirth": "2005-06-15"
				}""";
		mockMvc.perform(post(URL).with(csrf()).with(httpBasic("deepa","ashish"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(inValidJson)).andExpect(status().isBadRequest());
		verify(studentService,times(0)).save(any(StudentCreateDto.class));
	}
    @Test
	@DisplayName("POST /students/add - malformed JSON returns 400")
	public void createStudent_whenInvalidJson_thenReturns400()throws Exception{
		String inValidJson= """
				"name":"ashish",
				 "email": "john.doe@example.com",
              "phoneNumber": "9876543210",
              "enrollmentDate": "2023-09-01",
              "status": true,
              "guardianName": "Mr Guardian",
              "dateOfBirth": "2005-06-15"
				""";
		mockMvc.perform(post(URL).with(httpBasic("deepa","ashish"))
				.with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inValidJson)).andExpect(status().isBadRequest());
		verify(studentService,times(0)).save(any(StudentCreateDto.class));
		verifyNoInteractions(studentService);
}


	@Test
	@DisplayName("POST /students/add - duplicate student returns 409 Conflict")
	public void createStudent_whenDuplicate_thenReturns409() throws Exception {
		// Arrange: JSON representing an already existing student
		String duplicateJson = """
    {
      "name": "Ashish",
      "email": "john.doe@example.com",
      "phoneNumber": "9876543210",
      "enrollmentDate": "2023-09-01",
      "status": true,
      "guardianName": "Mr Guardian",
      "dateOfBirth": "2005-06-15"
    }
    """;

		// Mock behavior: service throws custom exception when duplicate found
		when(studentService.save(any(StudentCreateDto.class)))
				.thenThrow(new DuplicateResourceException("Student already exists with same email"));

		// Act + Assert
		mockMvc.perform(post(URL)
						.with(httpBasic("deepa", "ashish"))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(duplicateJson))
				.andExpect(status().isConflict())  // 409 Conflict
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Conflict"))
				.andExpect(jsonPath("$.message").value("Student already exists with same email"));

		verify(studentService, times(1)).save(any(StudentCreateDto.class));
	}
}
