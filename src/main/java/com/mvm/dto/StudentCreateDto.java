package com.mvm.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class StudentCreateDto {//having 14 fields
	@NotNull
	private String name;

	@Email
	private String email;

	private String address;

	@NotNull
	private String phoneNumber;

	private String course;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate enrollmentDate;

	@NotNull
	private Boolean status;

	@NotNull
	private String guardianName;

	private String guardianContact;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private String classSection;
	private Integer rollNumber;
	private String bloodGroup;
	private String emergencyContact;
}
