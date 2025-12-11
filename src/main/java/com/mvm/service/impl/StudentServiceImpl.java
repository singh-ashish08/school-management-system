package com.mvm.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvm.dto.StudentCreateDto;
import com.mvm.dto.StudentDto;
import com.mvm.dto.StudentResponseDto;
import com.mvm.entity.Student;
import com.mvm.exception.ResourceNotFoundException;
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
		Student studentDB = modelMapper.map(student, Student.class);
		Student savedStudent = studentRepository.save(studentDB);
		return modelMapper.map(savedStudent, StudentCreateDto.class);
	}

	@Override
	public StudentResponseDto getResponseById(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new RuntimeException("Student not found with the given id: " + id);
		} else {
			return modelMapper.map(student.get(), StudentResponseDto.class);
		}
	}

	@Override
	public StudentDto getDtoById(long id) {
		Optional<Student> student = studentRepository.findById(id);
		
		if (student.isPresent()) {
			return modelMapper.map(student.get(), StudentDto.class);
		} else {
			throw new RuntimeException("Student not found with the given id: " + id);
		}
	}

	@Override
	public List<StudentResponseDto> getAllResponse() {
		List<Student> allStudents = studentRepository.findAll();
		return allStudents.stream().map(s -> modelMapper.map(s, StudentResponseDto.class)).toList();
	}

    @Override
    public StudentCreateDto update(StudentCreateDto studentDto, long id) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with the given id: " + id));

        // Update fields
        existingStudent.setAddress(studentDto.getAddress());
        existingStudent.setBloodGroup(studentDto.getBloodGroup());
        existingStudent.setClassSection(studentDto.getClassSection());
        existingStudent.setCourse(studentDto.getCourse());
        existingStudent.setDateOfBirth(studentDto.getDateOfBirth());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setEmergencyContact(studentDto.getEmergencyContact());
        existingStudent.setEnrollmentDate(studentDto.getEnrollmentDate());
        existingStudent.setGuardianContact(studentDto.getGuardianContact());
        existingStudent.setGuardianName(studentDto.getGuardianName());
        existingStudent.setName(studentDto.getName());
        existingStudent.setPhoneNumber(studentDto.getPhoneNumber());
        existingStudent.setRollNumber(studentDto.getRollNumber());
        existingStudent.setStatus(studentDto.getStatus());

        // Save updated entity
        Student updatedStudent = studentRepository.save(existingStudent);

        return modelMapper.map(updatedStudent, StudentCreateDto.class);
    }


    @Override
	public void delete(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new RuntimeException("Student not found with the given id: " + id);
		} else {
			studentRepository.deleteById(id);
		}
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

	@Override
	public StudentResponseDto findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
