package com.li.student.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.li.common.SqlSessionFactoryUtils;
import com.li.student.domain.Student;
import com.li.student.persistence.StudentDao;
import com.li.student.persistence.StudentDaoImpl;

public class StudentService {
	
	private StudentDao dao;
	
	public void setDao(StudentDao dao) {
		this.dao=dao;
	}
	
	public Student getById(String id) {
		return dao.getById(id);
	}
	
	public List<Student> getByBlurredName(String name){
		return dao.getByBlurredName(name);
	}
	
	public void save(Student s) {
		dao.save(s);
	}
	
	public void deleteStudent(String id) {
		dao.deleteStudent(id);
	}
	
	public void updateName(Student s) {
		dao.updateName(s);
	}
	
	public static void main(String[] args) {
		
		StudentService ss=new StudentService();
		StudentDaoImpl impl=new StudentDaoImpl();
		ss.setDao(impl);
		
		ss.deleteStudent("1999");
		
		List<Student> list = ss.getByBlurredName("wang");
		for(Student s : list) {
			System.out.println(s);
		}
	}

}