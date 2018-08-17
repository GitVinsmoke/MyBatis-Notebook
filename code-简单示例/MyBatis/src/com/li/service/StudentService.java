package com.li.service;

import java.util.List;

import com.li.domain.Student;

public interface StudentService {
	
	public Student getById(String id);
	
	public List<Student> getByBlurredName(String name);
	
	public void save(Student s);
	
	public void deleteStudent(String id);
	
	public void updateName(Student s);
	
}