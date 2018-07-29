package com.ef.inputs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConverterAndValidateThresholdTest {
	
	private ConverterAndValidateThreshold converterAndValidateThreshold = new ConverterAndValidateThreshold();

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {		
		converterAndValidateThreshold.convert(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {		
		converterAndValidateThreshold.convert("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidWord() {		
		converterAndValidateThreshold.convert("o");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testZero() {		
		converterAndValidateThreshold.convert("0");
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void testNegative() {		
		converterAndValidateThreshold.convert("-1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSum() {		
		converterAndValidateThreshold.convert("1+1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRacional() {		
		converterAndValidateThreshold.convert("1.1");
	}
	
	@Test
	public void testOne() {	
		String one = "1";
		Long threshold = (Long) converterAndValidateThreshold.convert(one);		
		assertEquals(Long.valueOf(one), threshold);	
	}
	
	@Test
	public void testTen() {	
		String ten = "10";
		Long threshold = (Long) converterAndValidateThreshold.convert(ten);		
		assertEquals(Long.valueOf(ten), threshold);	
	}
	
	@Test
	public void testHundred() {	
		String hundred = "100";
		Long threshold = (Long) converterAndValidateThreshold.convert(hundred);		
		assertEquals(Long.valueOf(hundred), threshold);	
	}
	
	@Test
	public void testThousand() {	
		String thousand = "1000";
		Long threshold = (Long) converterAndValidateThreshold.convert(thousand);		
		assertEquals(Long.valueOf(thousand), threshold);	
	}
	
	@Test
	public void testMillion() {	
		String million = "100000";
		Long threshold = (Long) converterAndValidateThreshold.convert(million);		
		assertEquals(Long.valueOf(million), threshold);	
	}
	
	
	
	
	

}
