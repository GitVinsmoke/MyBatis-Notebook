package com.li.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/*SqlSessionFactory工具类，将使用单例模式创建SqlSessionFactory*/

public class SqlSessionFactoryUtils {
	
	private static class SqlSessionFactoryClass{
	
		static {
			System.out.println("创建SqlSessionFactory对象的静态代码块执行了！");
			
			// 1. 创建SqlSessionFactoryBuilder对象
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			try {
				// 2. 加载SqlMapConfig.xml配置文件
				InputStream inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
				// 3. 创建SqlSessionFactory对象
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
