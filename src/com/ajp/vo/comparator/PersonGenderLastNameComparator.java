package com.ajp.vo.comparator;

import java.util.Comparator;

import com.ajp.vo.Person;

public class PersonGenderLastNameComparator implements Comparator<Person> {

	private static PersonGenderLastNameComparator instance;
	
	public static PersonGenderLastNameComparator getInstance() {
		if (instance==null) 
			instance = new PersonGenderLastNameComparator();
		return instance;
	}

	protected PersonGenderLastNameComparator() {
	}

	@Override
	public int compare(Person o1, Person o2) {
		if (o1.isGender() != o2.isGender())
			return ((Boolean)o1.isGender()).compareTo(o2.isGender());
		return o1.getLastName().compareTo(o2.getLastName());
	}

}
