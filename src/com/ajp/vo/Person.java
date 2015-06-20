package com.ajp.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ajp.vo.serializer.CustomDateSerializer;

public class Person {

	public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	
	private String firstName;
	private String lastName;
	private boolean gender;
	private String color;
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date birth;
	
	public Person(String firstName, String lastName, boolean gender, String color, Date birth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.color = color;
		this.birth = birth;
	}

	public String toString() {
		return "firstName = ["+firstName+"], lastName = ["+lastName+"], gender = ["+(gender?"M":"F")+"], color = ["+color+"], birth = ["+formatter.format(birth)+"]";
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
}
