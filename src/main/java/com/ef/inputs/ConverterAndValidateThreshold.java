package com.ef.inputs;

public class ConverterAndValidateThreshold implements ConverterAndValidate {


	@Override 
	public Object convert(final String parameter) {
		
		Long result =  null;
		
		if (parameter != null) {
			final String parameterEvaluate = parameter.trim();
			if (parameterEvaluate.matches("\\d+")) {
				result = Long.parseLong(parameterEvaluate);
			}			
		} 
		
		if ((result == null) || (result <= 0) ) {
			throw new IllegalArgumentException("Invalid " + ParameterType.THRESHOLD.getDescription() + ": " + parameter);
		}
		
		return result;	
		
	}
	
}
