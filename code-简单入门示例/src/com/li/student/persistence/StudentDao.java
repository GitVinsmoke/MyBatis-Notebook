package com.li.student.persistence;

import java.util.List;

import com.li.student.domain.Student;

public interface StudentDao {
	
	/*����idɾ��ѧ��*/
	void deleteStudent(String id);
	
	/*���ѧϰ�����ݿ�*/
	void save(Student s);
	
	/*ͨ��id��ȡѧ����Ϣ*/
	Student getById(String id);
	
	/*ͨ�����ֽ���ģ����ѯ*/
	List<Student> getByBlurredName(String name);
	
	/*����ѧ������*/
	void updateName(Student s);
}
