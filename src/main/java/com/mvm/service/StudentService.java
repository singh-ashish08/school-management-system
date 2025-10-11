package com.mvm.service;

import java.util.List;

import com.mvm.dto.StudentCreateDto;
import com.mvm.dto.StudentDto;
import com.mvm.dto.StudentResponseDto;

public interface StudentService {
	public StudentCreateDto save(StudentCreateDto student);

	public StudentDto getAllDetailsOfStudent(long id);

	public List<StudentDto> getAllStudents();

	public StudentResponseDto getResponseById(long id);

	public List<StudentResponseDto> getAllResponse();

	public StudentDto getDtoById(long id);

	public void delete(long id);

	public StudentResponseDto findByName(String name);

	StudentCreateDto update(StudentCreateDto student, long id);

}
