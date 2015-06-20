package com.ajp.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ajp.PersonReader;
import com.ajp.vo.Person;
import com.ajp.vo.comparator.PersonBirthComparator;
import com.ajp.vo.comparator.PersonGenderLastNameComparator;
import com.ajp.vo.comparator.PersonLastNameComparator;

public class PersonTest {

	List<Person> persons;
	
	@Before
	public void initPersonList() {
		persons = new ArrayList<Person>();
		persons.add(new Person("Adnrey", "Zusko", true, "black", new Date(new GregorianCalendar(1976, 6, 18).getTimeInMillis())));
		persons.add(new Person("Pavel", "Zusko", true, "yellow", new Date(new GregorianCalendar(1979, 3, 15).getTimeInMillis())));
		persons.add(new Person("John", "Smith", true, "black", new Date(new GregorianCalendar(1924, 9, 18).getTimeInMillis())));
		persons.add(new Person("Elena", "Zusko", false, "red", new Date(new GregorianCalendar(1979, 6, 6).getTimeInMillis())));
		persons.add(new Person("Freddy", "Mercury", true, "black", new Date(new GregorianCalendar(1946, 8, 5).getTimeInMillis())));
	}
	
	@Test
	public void compareTest1() {
		List<Person> personsCopy = new ArrayList<Person>(persons);
		Collections.sort(personsCopy, PersonBirthComparator.getInstance());
		//System.out.println(personsCopy);
		Assert.assertTrue("John".equals(personsCopy.get(0).getFirstName()));
	}

	@Test
	public void compareTest2() {
		List<Person> personsCopy = new ArrayList<Person>(persons);
		Collections.sort(personsCopy, PersonGenderLastNameComparator.getInstance());
		System.out.println(personsCopy);
		Assert.assertTrue("Elena".equals(personsCopy.get(0).getFirstName()));
	}

	@Test
	public void compareTest3() {
		List<Person> personsCopy = new ArrayList<Person>(persons);
		Collections.sort(personsCopy, PersonLastNameComparator.getInstance());
		//System.out.println(personsCopy);
		Assert.assertTrue("Zusko".equals(personsCopy.get(0).getLastName()));
	}
	
	@Test
	public void loadTest1() throws IOException {
		List<Person> persons = new PersonReader().process("resource\\person1.txt", "B");
		Assert.assertTrue(persons.size()==4);
		Assert.assertTrue("Smith".equals(persons.get(0).getFirstName()));
	}
	
	@Test 
	public void processLineTest() {
		Person person = new PersonReader().processLine("Zusko Pavel M yellow 4/15/1979");
		Assert.assertTrue("yellow".equals(person.getColor()));
	}
}
