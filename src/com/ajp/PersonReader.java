package com.ajp;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.ajp.vo.Person;
import com.ajp.vo.comparator.PersonBirthComparator;
import com.ajp.vo.comparator.PersonGenderLastNameComparator;
import com.ajp.vo.comparator.PersonLastNameComparator;

public class PersonReader {

	public static void main(String a[]) {
		try {
			new PersonReader().process(a[0], a[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Person> process(String fileName, String sort) throws IOException {
		int count = 0;
		List<Person> persons = new ArrayList<Person>();
		for (String line : Files.readAllLines(FileSystems.getDefault().getPath(fileName))) {
			count ++;
			Person person = processLine(line);
			if (person!=null)
				persons.add(person);
		}
		
		System.out.println("Read ["+count+"] lines, recognized ["+persons.size()+"] person records");
		
		switch (sort) {
			case "G" : 
				Collections.sort(persons, PersonGenderLastNameComparator.getInstance());
				break;
			case "L" :
				Collections.sort(persons, PersonLastNameComparator.getInstance());
				break;
			case "B" :
				Collections.sort(persons, PersonBirthComparator.getInstance());
				break;
		}
		
		for (Person person: persons) {
			System.out.println(person);
		}
		
		return persons;
	}

	public Person processLine(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, " |,");
		if (tokenizer.countTokens() != 5) 
			return null;
		String firstName = tokenizer.nextToken();
		String lastName = tokenizer.nextToken();
		String genderStr = tokenizer.nextToken();
		if (!"M".equals(genderStr) && !"F".equals(genderStr)) 
			return null;
		boolean gender = "M".equals(genderStr);
		String color = tokenizer.nextToken();
		Date birth = null;
		try {
			birth = Person.formatter.parse(tokenizer.nextToken());
		} catch (ParseException e) {
			return null;
		}
		return new Person(firstName, lastName, gender, color, birth);
	}

}
