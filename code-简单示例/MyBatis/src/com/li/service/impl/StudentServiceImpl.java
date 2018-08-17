package com.li.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.li.common.SqlSessionFactoryUtils;
import com.li.domain.Student;
import com.li.mapper.StudentMapper;
import com.li.service.StudentService;

public class StudentServiceImpl implements StudentService {
	
	private StudentMapper studentMapper;

	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper=studentMapper;
	}
	
	@Override
	public Student getById(String id) {
		return studentMapper.getById(id);
	}

	@Override
	public List<Student> getByBlurredName(String name) {
		return studentMapper.queryByBlurredName(name);
	}

	@Override
	public void save(Student s) {
		studentMapper.insertStu(s);
	}

	@Override
	public void deleteStudent(String id) {
		studentMapper.deleteStu(id);
	}

	@Override
	public void updateName(Student s) {
		studentMapper.updateName(s);
	}

	public static void main(String[] args) {
		StudentServiceImpl service= new StudentServiceImpl();
		SqlSession session= SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		service.setStudentMapper(session.getMapper(StudentMapper.class));
		
		try {
			List<Student> list=service.getByBlurredName("li");
			for(Student s : list) {
				System.out.println(s);
			}
		} finally {
			session.close();
		}
	}
	
}
