package com.mvm.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StudentCreateDto {
	private long id;
	@NotEmpty
	private String name;
	@Email
	private String email;
	private String address;
	@NotEmpty
	private String phoneNumber;
	private String course;
	private LocalDate enrollmentDate;
	private boolean status;
	@NotEmpty
	private String guardianName;
	private String guardianContact;
	@NotEmpty
	private String dateOfBirth;
	private String classSection;
	private int rollNumber;
	private String bloodGroup;
	private String emergencyContact;
}
