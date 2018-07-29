package com.ef.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.ef.repository.to.Duration;

public class Utils {

	public static Timestamp getTimestamp(LocalDateTime localDateTime) {
		Timestamp result = null; 
		
		if (localDateTime != null) {
			result = Timestamp.valueOf(localDateTime);
		}
		
		return result;
	}

	public static LocalDateTime getLocalDateTime(Timestamp timestamp) {		
		LocalDateTime result = null;

		if (timestamp != null) {
			result = timestamp.toLocalDateTime();
		}
		
		return result;
	}
	
	public static Duration getDuration(String charDuration) {		
		Duration result = null;

		if (charDuration != null) {
			if (charDuration.length() > 0) {
				result = Duration.getDurationByKey(charDuration.charAt(0));
			}
		}
		
		return result;
	}
	
	public static String getDurationString(Duration duration) {		
		String result = null;

		if (duration != null) {
			result = String.valueOf(duration.getKey());
		}
		
		return result;
	}
}
