package com.li.student.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.li.common.SqlSessionFactoryUtils;
import com.li.student.domain.Student;
import com.li.student.domain.StudentMapper;

public class StudentDaoImpl implements StudentDao {

	@Override
	public void save(Student s) {
		SqlSession session=SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		try {
			StudentMapper mapper=session.getMapper(StudentMapper.class);
			mapper.insertStu(s);
			/*对数据库做更新操作时，如果不提交，数据库不改变而且还不会报错*/
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public Student getById(String id) {
		SqlSession session=SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		Student s=null;
		try {
//			s=session.selectOne("com.li.student.domain.StudentMapper.queryById", "2015");
			StudentMapper mapper=session.getMapper(StudentMapper.class);
			s=mapper.queryById("2015");
		} finally {
			session.close();
		}
		return s;
	}

	@Override
	public List<Student> getByBlurredName(String name) {
		SqlSession session=SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		List<Student> list=null;
		try {
			StudentMapper mapper=session.getMapper(StudentMapper.class);
			list=mapper.queryByBlurredName(name);
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public void deleteStudent(String id) {
		SqlSession session=SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		try {
			StudentMapper mapper=session.getMapper(StudentMapper.class);
			mapper.deleteStu(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void updateName(Student s) {
		SqlSession session=SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
		try {
			StudentMapper mapper=session.getMapper(StudentMapper.class);
			mapper.updateName(s);
			session.commit();
		} finally {
			session.close();
		}
	}
	
}
