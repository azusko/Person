package com.ajp.vo.comparator;

import java.util.Comparator;

import com.ajp.vo.Person;

public class PersonLastNameComparator implements Comparator<Person>{

	private static PersonLastNameComparator instance;
	
	public static PersonLastNameComparator getInstance() {
		if (instance==null) 
			instance = new PersonLastNameComparator();
		return instance;
	}

	protected PersonLastNameComparator() {
	}

	@Override
	public int compare(Person o1, Person o2) {
		return -1*(o1.getLastName().compareTo(o2.getLastName()));
	}

}
