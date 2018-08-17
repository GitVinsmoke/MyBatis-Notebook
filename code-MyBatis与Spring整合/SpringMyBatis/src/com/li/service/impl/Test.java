package com.li.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.li.domain.Student;
import com.li.service.StudentService;

public class Test {

	public static void main(String[] args) {
		String r1="classpath:spring/applicationContext-dao.xml";
		String r2="classpath:spring/applicationContext-service.xml";
		String r3="classpath:spring/applicationContext-transaction.xml";
		ApplicationContext ctx=new ClassPathXmlApplicationContext(r1,r2, r3);
		
		StudentService service = ctx.getBean("studentServiceImpl", StudentService.class);
		Student s = service.queryById("2015");
		System.out.println(s);
	}

}
