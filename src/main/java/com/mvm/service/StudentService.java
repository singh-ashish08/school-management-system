package com.mvm.service;

import com.mvm.dto.StudentCreateDto;
import com.mvm.dto.StudentDto;
import com.mvm.dto.StudentResponseDto;

public interface StudentService {
	public StudentCreateDto save(StudentCreateDto student);

	public StudentResponseDto getById(long id);

	public StudentDto getFullStudent(long id);	

	public StudentCreateDto update(StudentCreateDto student);

	public void delete(long id);

}
