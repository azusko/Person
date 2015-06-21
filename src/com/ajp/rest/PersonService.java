package com.ajp.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.ajp.PersonReader;
import com.ajp.vo.Person;
import com.ajp.vo.comparator.PersonBirthComparator;
import com.ajp.vo.comparator.PersonGenderLastNameComparator;
import com.ajp.vo.comparator.PersonLastNameComparator;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/records")
public class PersonService {

	private static List<Person> persons = new ArrayList<Person>();
	private static Object lock = new Object();

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		PersonReader personReader = new PersonReader();
		BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedInputStream));
		List<Person> uploaded = new ArrayList<Person>();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				Person person = personReader.processLine(line);
				if (person != null)
					uploaded.add(personReader.processLine(line));
			}

		} catch (IOException e) {
			return Response.status(200).entity("File upload error").build();
		}

		synchronized (lock) {
			persons = new ArrayList<Person>();
			persons.addAll(uploaded);
		}
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("gender")
	@Produces(MediaType.APPLICATION_JSON)
	public String produceGenderSorted() {
		System.out.println(persons);
		return produceSortedList(PersonGenderLastNameComparator.getInstance());
	}

	@GET
	@Path("birthdate")
	@Produces(MediaType.APPLICATION_JSON)
	public String produceBirthDateSorted() {
		return produceSortedList(PersonBirthComparator.getInstance());
	}

	@GET
	@Path("name")
	@Produces(MediaType.APPLICATION_JSON)
	public String produceLastNameSorted() {
		return produceSortedList(PersonLastNameComparator.getInstance());
	}

	private String produceSortedList(Comparator<Person> comparator) {
		ObjectMapper mapper = new ObjectMapper();
		List<Person> resultList = new ArrayList<Person>();
		synchronized (lock) {
			resultList.addAll(persons);
		}
		Collections.sort(resultList, comparator);
		String result = null;
		try {
			result = mapper.writeValueAsString(resultList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
