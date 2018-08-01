package com.li.student.domain;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface StudentMapper {
	
	Student queryById(String id);
		
	@Select("SELECT * FROM stu_info WHERE stu_no  = #{id}")
	Student getById(String id);
	
	List<Student> queryByBlurredName(String name);

	void insertStu(Student s);
	
	void deleteStu(String id);
	
	void updateName(Student s);
}
