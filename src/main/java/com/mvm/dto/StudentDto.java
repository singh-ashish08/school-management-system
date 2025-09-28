package com.mvm.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
	private long id;
	private String name;
	private String email;
	private String address;
	private String phoneNumber;
	private String course;
	private LocalDate enrollmentDate;
	private boolean status;
	private String guardianName;
	private String guardianContact;
	private String dateOfBirth;
	private String classSection;
	private int rollNumber;
	private String bloodGroup;
	private String emergencyContact;
}
