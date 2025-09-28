package com.mvm.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String address;
	private String phoneNumber;
	private String course;
	private LocalDate enrollmentDate;
	private Boolean status;
	private String guardianName;
	private String guardianContact;
	private String dateOfBirth;
	private String classSection;
	private Integer rollNumber;
	private String bloodGroup;
	private String emergencyContact;
}
