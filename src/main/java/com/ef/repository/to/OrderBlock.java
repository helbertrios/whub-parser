package com.ef.repository.to;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class OrderBlock {

	private Long id;
	private LocalDateTime orderDate;
	private LocalDateTime startDate;
	private Duration duration;
	private Long threshold;
	private Boolean orderCompleted;
	private LogFile logFile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Long getThreshold() {
		return threshold;
	}

	public void setThreshold(Long threshold) {
		this.threshold = threshold;
	}

	public Boolean getOrderCompleted() {
		return orderCompleted;
	}

	public void setOrderCompleted(Boolean orderCompleted) {
		this.orderCompleted = orderCompleted;
	}

	public LogFile getLogFile() {
		return logFile;
	}

	public void setLogFile(LogFile logFile) {
		this.logFile = logFile;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:
				result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

}
