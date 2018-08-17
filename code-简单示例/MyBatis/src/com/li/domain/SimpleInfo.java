package com.li.domain;

public class SimpleInfo {
	
	private String no;
	private String name;
	
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
	
	public SimpleInfo() {
		super();
	}
	
	public SimpleInfo(String no, String name) {
		super();
		this.no = no;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "SimpleInfo [no=" + no + ", name=" + name + "]";
	}
	
}
