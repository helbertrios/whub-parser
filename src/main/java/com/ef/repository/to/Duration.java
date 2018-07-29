package com.ef.repository.to;

public enum Duration {
	
	
	HOURLY('h'), DAILY('d');
	
	private Character key;
	
	private Duration(Character key) {
		this.key = key;
	}
	
	public static Duration getDuration(final String literal) {
		Duration result = null;
		for (Duration d : Duration.values()) {
			if (d.name().toLowerCase().equals(literal)) {
				result = d;
				break;
			}
		}	
		return result;
	}
	
	public static Duration getDurationByKey(final char key) {
		Duration result = null;
		for (Duration d : Duration.values()) {
			if (d.getKey().equals(key)) {
				result = d;
				break;
			}
		}	
		return result;
	}

	public Character getKey() {
		return key;
	}
	
	
		

}
