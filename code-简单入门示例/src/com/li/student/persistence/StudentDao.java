package com.li.student.persistence;

import java.util.List;

import com.li.student.domain.Student;

public interface StudentDao {
	
	/*根据id删除学生*/
	void deleteStudent(String id);
	
	/*添加学习到数据库*/
	void save(Student s);
	
	/*通过id获取学生信息*/
	Student getById(String id);
	
	/*通过名字进行模糊查询*/
	List<Student> getByBlurredName(String name);
	
	/*更新学生姓名*/
	void updateName(Student s);
}
