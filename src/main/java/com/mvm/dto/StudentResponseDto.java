package com.mvm.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
	private String name;
	private String address;
	private String phoneNumber;
	private String course;
	private LocalDate enrollmentDate;
	private boolean status;
	private String guardianName;
	private String classSection;
	private int rollNumber;
	private String emergencyContact;

}
