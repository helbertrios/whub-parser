package com.ef.inputs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ef.UtilTest;
import com.ef.repository.to.Duration;

public class InputHandlerTest {

	private final InputHandler inputHandler = new InputHandler();
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull() {		
		final String[] arguments = null;				
		inputHandler.extractParameters(arguments);					
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {		
		final String[] arguments = {};				
		inputHandler.extractParameters(arguments);					
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithoutStartDate() {
		
		final String[] arguments = new String[] { "--duration=hourly", "--threshold=100", "--accesslog="+UtilTest.getFilePath()};				
		inputHandler.extractParameters(arguments);					
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithoutDuration() {		
		final String[] arguments = new String[] { "--startDate=2017-01-01.13:01:12", "--threshold=100", "--accesslog="+UtilTest.getFilePath()};				
		inputHandler.extractParameters(arguments);					
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithoutThreshold() {		
		final String[] arguments = new String[] { "--startDate=2017-01-01.13:01:12", "--duration=hourly", "--accesslog="+UtilTest.getFilePath()};				
		inputHandler.extractParameters(arguments);					
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithoutFilePath() {		
		final String[] arguments = new String[] { "--startDate=2017-01-01.13:01:12", "--duration=hourly", "--threshold=100"};				
		inputHandler.extractParameters(arguments);		
	}
	
	
	
	@Test
	public void testAllAtributes() {		
		final String[] arguments = new String[] { "--startDate=2017-01-01.13:01:12", "--duration=hourly", "--threshold=100", "--accesslog="+UtilTest.getFilePath()};				
		final InputData inputData = inputHandler.extractParameters(arguments);
		
				assertTrue(
							  (
								(ConverterAndValidateStartDateTest.validateStartDate(inputData.getStartDate(), 2017, 1, 1, 13, 1, 12)) &&
								(inputData.getDuration().equals(Duration.HOURLY)) &&
								(inputData.getThreshold().equals(100L)) &&
								(inputData.getFilePath().toAbsolutePath().toString().equals(UtilTest.getFilePath()))						
						      )						
						 );						
	}
	
	


	




	

}
