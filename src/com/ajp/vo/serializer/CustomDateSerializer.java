package com.ajp.vo.serializer;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.ajp.vo.Person;

public class CustomDateSerializer extends SerializerBase<Date> {

	public CustomDateSerializer() {
		super(Date.class, true);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonGenerationException {
		String format = Person.formatter.format(value);
		jgen.writeString(format);
	}

}
