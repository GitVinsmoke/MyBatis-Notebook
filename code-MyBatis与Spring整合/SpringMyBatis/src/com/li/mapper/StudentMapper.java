package com.li.mapper;

import java.util.List;

import com.li.domain.Student;

/*
 * 映射器类：动态代理
 * 1. SQL映射文件的命名空间必须是接口的完全限定名
 * 2. 接口方法必须与SQL id一致（除了使用注解的SQL语句）
 * 3. 接口的入参必须与parameterType类型一致
 * 4. 接口的返回值必须与resultType类型一致
 */

public interface StudentMapper {
	
	/*根据ID查询*/
	Student queryById(String id);
	
	/*根据名字进行模糊查询*/
	List<Student> queryByBlurredName(String name);
	
	/*插入操作*/
	void insertStu(Student s);
	
	/*删除操作*/
	void deleteStu(String id);
	
	/*更新操作*/
	void updateName(Student s);
}
