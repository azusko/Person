package com.ajp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ajp.vo.Person;

public class RESTTest {

	@Before
	public void testUpload() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost("http://localhost:8080/person/rest/records/upload");
			FileBody bin = new FileBody(new File("resource\\person1.txt"));
			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).build();

			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGetNameSorted() throws IOException {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://localhost:8080/person/rest/records/name");
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output = br.readLine();
			Assert.assertNotNull(output);

			ObjectMapper mapper = new ObjectMapper();
			List<Person> persons = mapper.readValue(output, new TypeReference<List<Person>>() {});
			Assert.assertNotNull(persons);
			Assert.assertTrue("Zusko".equals(persons.get(0).getLastName()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}