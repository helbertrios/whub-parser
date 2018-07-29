package com.ef.inputs;

public enum ParameterType {
		
	START_DATE("s", "startDate", "Start date (yyyy-MM-dd.HH:mm:ss)", new ConverterAndValidateStartDate()), 
	DURATION("d", "duration", "Duration (hourly or daily)", new ConverterAndValidateDuration()), 
	THRESHOLD("t", "threshold", "Threshold (Integer)", new ConverterAndValidateThreshold()), 
	FILEPATH("a", "accesslog", "File path (/home/access.log)", new ConverterAndValidateFilePath());
	
	private String key;
	private String longKey;
	private String description;
	private ConverterAndValidate converterAndValidate;

	private ParameterType(final String key, final String longKey, final String description, final ConverterAndValidate converterAndValidate) {
		this.key = key;
		this.longKey = longKey;
		this.description = description;
		this.converterAndValidate = converterAndValidate;
	}
		
	public String getKey() {
		return key;
	}

	public String getLongKey() {
		return longKey;
	}

	public String getDescription() {
		return description;
	}

	public Object convert(final String parameter) {
		return converterAndValidate.convert(parameter);
	}
	

}
