package com.ef.inputs;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class ConverterAndValidateStartDateTest {
	
	private ConverterAndValidateStartDate converterAndValidateStartDate = new ConverterAndValidateStartDate();
	
	public static boolean validateStartDate(LocalDateTime startDate, int year, int month, int dayOfMonth, int hour, int minute, int second) {
		if (
				(startDate.getYear() == year) &&
				(startDate.getMonthValue() == month) &&
				(startDate.getDayOfMonth() == dayOfMonth) &&
				(startDate.getHour() == hour) &&
				(startDate.getMinute() == minute) &&
				(startDate.getSecond() == second) 
			) {
			
			return true;
		}
		return false;
	}


	@Test(expected = IllegalArgumentException.class)
	public void testNull() {		
		converterAndValidateStartDate.convert(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {		
		converterAndValidateStartDate.convert("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidString() {		
		converterAndValidateStartDate.convert("sadcsdsdds");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidOnlyDate() {		
		converterAndValidateStartDate.convert("2017-01-01");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidOnlyTime() {		
		converterAndValidateStartDate.convert("13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFormat1() {		
		converterAndValidateStartDate.convert("2017-01-01 13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidFormat2() {		
		converterAndValidateStartDate.convert("2017/01/01.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDateFebruary29() {		
		converterAndValidateStartDate.convert("2017-02-29.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDateFebruary30() {		
		converterAndValidateStartDate.convert("2017-02-30.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDateApril31() {		
		converterAndValidateStartDate.convert("2017-04-31.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSecond() {		
		converterAndValidateStartDate.convert("2017-01-01.13:00:60");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMinute() {		
		converterAndValidateStartDate.convert("2017-01-01.13:60:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHour() {		
		converterAndValidateStartDate.convert("2017-01-01.24:00:00");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDayZero() {		
		converterAndValidateStartDate.convert("2017-01-00.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDay() {		
		converterAndValidateStartDate.convert("2017-01-32.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMonthZero() {		
		converterAndValidateStartDate.convert("2017-00-01.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMonth() {		
		converterAndValidateStartDate.convert("2017-13-01.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidYearZero1() {		
		converterAndValidateStartDate.convert("0-01-01.13:00:00");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidYearZero2() {		
		converterAndValidateStartDate.convert("00-01-01.13:00:00");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidYearZero3() {		
		converterAndValidateStartDate.convert("000-01-01.13:00:00");
	}
	
	
	@Test
	public void testValidDateOK1() {		
		LocalDateTime localDateTime = (LocalDateTime) converterAndValidateStartDate.convert("2017-01-12.13:21:32");
		assertTrue(validateStartDate(localDateTime, 2017, 1, 12, 13, 21,32));
	}
	
	@Test
	public void testValidDateOK2() {		
		LocalDateTime localDateTime = (LocalDateTime) converterAndValidateStartDate.convert("2017-01-01.13:00:00");
		assertTrue(validateStartDate(localDateTime, 2017, 1, 1, 13, 0,0));
	}
	
}
