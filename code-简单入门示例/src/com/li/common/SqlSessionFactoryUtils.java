package com.li.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/*SqlSessionFactory�����࣬��ʹ�õ���ģʽ����SqlSessionFactory*/

public class SqlSessionFactoryUtils {
	
	private static class SqlSessionFactoryClass{
	
		static {
			System.out.println("����SqlSessionFactory����ľ�̬�����ִ���ˣ�");
			
			// 1. ����SqlSessionFactoryBuilder����
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			try {
				// 2. ����SqlMapConfig.xml�����ļ�
				InputStream inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
				// 3. ����SqlSessionFactory����
				sessionFaction=builder.build(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static SqlSessionFactory sessionFaction;
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return SqlSessionFactoryClass.sessionFaction;
	}
	
	public static void main(String[] args) {
		System.out.println(SqlSessionFactoryUtils.getSqlSessionFactory());
	}
}
