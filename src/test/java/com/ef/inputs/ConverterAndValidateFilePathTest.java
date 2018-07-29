package com.ef.inputs;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.junit.Test;

import com.ef.UtilTest;

public class ConverterAndValidateFilePathTest {

	private ConverterAndValidateFilePath converterAndValidateFilePath = new ConverterAndValidateFilePath();
	

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {		
		converterAndValidateFilePath.convert(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {
		converterAndValidateFilePath.convert("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFileNotExists() {
		final String parameter = System.getProperty("user.dir") + File.separator + "shjsdhdjshdj.log";
		converterAndValidateFilePath.convert(parameter);
	}
	
	@Test
	public void testValid() {		
		final String expected = UtilTest.getFilePath();
		final String actual = ((Path) converterAndValidateFilePath.convert(expected)).toAbsolutePath().toString();		
		assertEquals(expected, actual);
		
	}

}
