package com.li.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.li.domain.Student;
import com.li.mapper.StudentMapper;
import com.li.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	@Qualifier(value="studentMapper")
	private StudentMapper studentMapper;
	
	@Override
	public Student queryById(String id) {
		return studentMapper.queryById(id);
	}

}
