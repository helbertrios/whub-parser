package com.ef.repository.to;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LineLogFile {

	private Integer lineNumber;
	private LocalDateTime accessDate;
	private String ip;
	private String action;
	private Integer code;
	private String accessInformation;
	private String source;

	public LineLogFile() {
		super();
	}

	public LineLogFile(Integer lineNumber, LocalDateTime accessDate, String ip, String action, Integer code,
			String accessInformation, String source) {
		super();
		this.lineNumber = lineNumber;
		this.accessDate = accessDate;
		this.ip = ip;
		this.action = action;
		this.code = code;
		this.accessInformation = accessInformation;
		this.source = source;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public LocalDateTime getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(LocalDateTime accessDate) {
		this.accessDate = accessDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAccessInformation() {
		return accessInformation;
	}

	public void setAccessInformation(String accessInformation) {
		this.accessInformation = accessInformation;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getSimpleName());
		result.append(" { ");

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		List<Field> fieldWanted = new ArrayList<Field>();

		// print field names paired with their values
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.getName().equals("newLine")) {
				continue;
			}
			fieldWanted.add(field);

		}

		for (int i = 0; i < fieldWanted.size(); i++) {
			Field field = fieldWanted.get(i);			
			
			if (i != 0) {				
				result.append(", ");				
			}
			
			
			try {
				result.append(field.getName());
				result.append(": ");
				result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
		}

		result.append(" }");
		return result.toString();
	}

}
