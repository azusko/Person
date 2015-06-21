package com.ajp.vo.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.ajp.vo.Person;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
		Date result = null;
		try {
			JsonToken token = jp.getCurrentToken();
			if (token.equals(JsonToken.VALUE_STRING)) {
				result = Person.formatter.parse(jp.readValueAs(String.class));
			}
		} catch (ParseException e) {
			throw ctx.mappingException(Date.class);
		}
		return result;
	}

}
