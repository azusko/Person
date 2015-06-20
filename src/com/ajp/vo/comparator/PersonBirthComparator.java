package com.ajp.vo.comparator;

import java.util.Comparator;

import com.ajp.vo.Person;

public class PersonBirthComparator implements Comparator<Person> {

	private static PersonBirthComparator instance;
	
	public static PersonBirthComparator getInstance() {
		if (instance==null) 
			instance = new PersonBirthComparator();
		return instance;
	}

	protected PersonBirthComparator() {
	}
	
	@Override
	public int compare(Person o1, Person o2) {
		return o1.getBirth().compareTo(o2.getBirth());
	}
	

}
