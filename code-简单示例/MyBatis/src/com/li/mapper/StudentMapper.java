package com.li.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.li.domain.QueryVo;
import com.li.domain.Student;

/*
 * ӳ�����ࣺ��̬����
 * 1. SQLӳ���ļ��������ռ�����ǽӿڵ���ȫ�޶���
 * 2. �ӿڷ���������SQL idһ�£�����ʹ��ע���SQL��䣩
 * 3. �ӿڵ���α�����parameterType����һ��
 * 4. �ӿڵķ���ֵ������resultType����һ��
 */

public interface StudentMapper {
	
	/*����ID��ѯ*/
	Student queryById(String id);
	
	/*ע�ⷽʽ*/
	@Select("SELECT * FROM stu_info WHERE stu_no  = #{id}")
	Student getById(String id);
	
	/*�������ֽ���ģ����ѯ*/
	List<Student> queryByBlurredName(String name);
	
	/*�����װ��DAO*/
	List<Student> getStudentByQueryVo(QueryVo v);

	/*�������*/
	void insertStu(Student s);
	
	/*ɾ������*/
	void deleteStu(String id);
	
	/*���²���*/
	void updateName(Student s);
}
