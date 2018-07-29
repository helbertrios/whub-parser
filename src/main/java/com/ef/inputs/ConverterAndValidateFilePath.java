package com.ef.inputs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConverterAndValidateFilePath implements ConverterAndValidate {

	public static final String FILE_NAME = "access.log"; 
			
	
	@Override 
	public Object convert(final String parameter) {

		Path result = null;
				
		if (parameter == null) {
			throw new IllegalArgumentException("Invalid " + ParameterType.FILEPATH.getDescription() + ": " + parameter);
		} else if (!parameter.equals("")) {
			result = Paths.get(parameter);
		}
				
		if (result == null) {
			throw new IllegalArgumentException("Invalid " + ParameterType.FILEPATH.getDescription() + ": " + parameter);
		} else {
			if (!Files.exists(result)) {
				throw new IllegalArgumentException("File does not exists! "+parameter);
			}
		}
		
		return result;
	}
	
}
