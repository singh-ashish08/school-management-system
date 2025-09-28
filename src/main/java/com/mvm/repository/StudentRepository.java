package com.mvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvm.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	

}
