package com.mvm.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvm.dto.StudentCreateDto;
import com.mvm.dto.StudentDto;
import com.mvm.dto.StudentResponseDto;
import com.mvm.entity.Student;
import com.mvm.repository.StudentRepository;
import com.mvm.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentCreateDto save(StudentCreateDto student) {
		// TODO Auto-generated method stub
		Student studentDB = modelMapper.map(student, Student.class);
		Student savedStudent = studentRepository.save(studentDB);
		return modelMapper.map(savedStudent, StudentCreateDto.class);
	}

	@Override
	public StudentResponseDto getResponseById(long id) {
		Student student = studentRepository.findById(id).get();
		return modelMapper.map(student, StudentResponseDto.class);
	}

	@Override
	public StudentDto getDtoById(long id) {
		Student student = studentRepository.findById(id).get();
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public List<StudentResponseDto> getAllResponse() {
		List<Student> allStudents = studentRepository.findAll();
		return allStudents.stream().map(s -> modelMapper.map(s, StudentResponseDto.class)).toList();
	}

	@Override
	public StudentCreateDto update(StudentCreateDto student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		Student student = studentRepository.findById(id).get();
		studentRepository.delete(student);
	}

	@Override
	public StudentDto getAllDetailsOfStudent(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getAllStudents() {
		// TODO Auto-generated method stub
		List<Student> all = studentRepository.findAll();
		List<StudentDto> list = all.stream().map(x -> modelMapper.map(x, StudentDto.class)).toList();
		return list;
	}

}
