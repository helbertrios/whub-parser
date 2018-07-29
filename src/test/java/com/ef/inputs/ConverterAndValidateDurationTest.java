package com.ef.inputs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ef.repository.to.Duration;

public class ConverterAndValidateDurationTest {
	
	private ConverterAndValidateDuration  converterAndValidateDuration = new  ConverterAndValidateDuration();

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {		
		converterAndValidateDuration.convert(null);
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {		
		converterAndValidateDuration.convert("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalid() {		
		converterAndValidateDuration.convert("hhjhj");

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIInvalidHourlyCase() {		
		converterAndValidateDuration.convert("hoUrly");

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInValidDailyCase() {		
		 converterAndValidateDuration.convert("Daily");

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIInvalidHourlyUpperCase() {		
		converterAndValidateDuration.convert("HOURLY");

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInValidDailyUpperCase() {		
		converterAndValidateDuration.convert("DAILY");
	}

	
	@Test
	public void testIValidHourly() {		
		Duration duration = (Duration) converterAndValidateDuration.convert("hourly");
		assertTrue(Duration.HOURLY.equals(duration));
	}
	
	@Test
	public void testIValidDaily() {		
		Duration duration = (Duration) converterAndValidateDuration.convert("daily");
		assertTrue(Duration.DAILY.equals(duration));
	}

}
