package com.ef.inputs;

import com.ef.repository.to.Duration;

public class ConverterAndValidateDuration implements ConverterAndValidate {


	@Override 
	public Object convert(final String parameter) {

		final Duration retorno = Duration.getDuration(parameter);

		if (retorno == null) {
			throw new IllegalArgumentException("Invalid " + ParameterType.DURATION.getDescription() + ": " + parameter);
		} else {
			return retorno;	
		}
		
		
	}
	
}
