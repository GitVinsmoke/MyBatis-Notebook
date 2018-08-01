package com.li.student.domain;

public class Student {
	
	private String stu_no;	
	private String stu_name;
	private Integer stu_age;
	private String stu_gender;
	public String getStu_no() {
		return stu_no;
	}
	public void setStu_no(String stu_no) {
		this.stu_no = stu_no;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public Integer getStu_age() {
		return stu_age;
	}
	public void setStu_age(Integer stu_age) {
		this.stu_age = stu_age;
	}
	public String getStu_gender() {
		return stu_gender;
	}
	public void setStu_gender(String stu_gender) {
		this.stu_gender = stu_gender;
	}
	
	public Student(String stu_no, String stu_name, Integer stu_age, String stu_gender) {
		super();
		this.stu_no = stu_no;
		this.stu_name = stu_name;
		this.stu_age = stu_age;
		this.stu_gender = stu_gender;
	}

	public Student() {
		super();
	}
	@Override
	public String toString() {
		return "Student [stu_no=" + stu_no + ", stu_name=" + stu_name + ", stu_age=" + stu_age + ", stu_gender="
				+ stu_gender + "]";
	}
	
}
