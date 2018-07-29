package com.ef.inputs;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class ConverterAndValidateStartDate implements ConverterAndValidate {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd.HH:mm:ss").withResolverStyle(ResolverStyle.STRICT).withZone(ZoneOffset.UTC);

	
	@Override
	public Object convert(final String parameter) {
		LocalDateTime result;
		try {
			result = LocalDateTime.parse(parameter, FORMATTER);
		} catch (Exception e) {			
			throw new IllegalArgumentException("Invalid "+ParameterType.START_DATE.getDescription()+": "+parameter);
		}
		
		return result;
	}
	
	
}
