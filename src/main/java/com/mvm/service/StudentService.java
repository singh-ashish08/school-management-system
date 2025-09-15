package com.mvm.service;

import com.mvm.entity.Student;

public interface StudentService {
	public  Student save(Student student);

	public Student getById(long id);

	public Student update(Student student);

	public void delete(long id);

}
