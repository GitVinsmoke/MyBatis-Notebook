package com.li.domain;

import org.apache.ibatis.annotations.Param;

public class Student {
	
	private String no;	
	private String name;
	private Integer age;
	private String gender;
	
	private SimpleInfo simple;
	
	public SimpleInfo getSimple() {
		return simple;
	}
	public void setSimple(SimpleInfo simple) {
		this.simple = simple;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Student(@Param("no")String no, @Param("name")String name, @Param("age")Integer age, @Param("gender")String gender) {
		super();
		this.no = no;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}	
}
